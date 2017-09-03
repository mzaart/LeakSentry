package com.mzaart.leaksentry.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.leaksentry.aquery.$;
import static com.mzaart.leaksentry.aquery.Constructors.*;
import com.mzaart.leaksentry.base.BasePresenter;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;
import com.mzaart.leaksentry.gasInfo.GasInfoContract;
import com.mzaart.leaksentry.gasInfo.GasInfoPresenter;
import com.mzaart.leaksentry.time.TimeContract;
import com.mzaart.leaksentry.time.TimePresenter;
import com.mzaart.leaksentry.utils.rxUtils.bitmapObservableSource.BitmapObservableSource;

import com.mzaart.leaksentry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.mzaart.leaksentry.dagger.components.DaggerPresenterComponent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements GasInfoContract.GasInfoView, TimeContract.TimeView {

    private static int[] styles = {R.style.Safe, R.style.Caution, R.style.Warning, R.style.Dangerous};
    private static int style = styles[0];

    public GasInfoContract.ViewActions gasInfoPresenter;
    public TimeContract.ViewActions timePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(style);
        setContentView(R.layout.activity_main);

        // get starting intent
        Intent intent = getIntent();
        if (intent != null) {
            String message = intent.getStringExtra("message");
            if (message != null)
                $.toast(this, message);
        }

        // set up gasInfoPresenter
        List<BasePresenter> presenters = (List<BasePresenter>) getLastCustomNonConfigurationInstance();

        if (presenters == null) {
            DaggerPresenterComponent.builder()
                    .presenterModule(new PresenterModule())
                    .build()
                    .inject(this);

            ((MyApplication) getApplication()).getComponent().inject((GasInfoPresenter) gasInfoPresenter);
        } else {
            gasInfoPresenter = (GasInfoContract.ViewActions) presenters.get(0);
            timePresenter = (TimeContract.ViewActions) presenters.get(1);
        }

        gasInfoPresenter.attachView(this);
        timePresenter.attachView(this);

        // set date
        $(this, R.id.current_time).text(new SimpleDateFormat("hh:mm a").format(new Date()));
        $(this, R.id.current_day).text(new SimpleDateFormat("EEE MMM d").format(new Date()));

        $(this).ready(() -> {
            // set background image
            $ bg = $(this, R.id.bg);
            Observable.defer(() ->
                    new BitmapObservableSource.Builder(this, bg.width(), bg.height())
                            .resources(getResources())
                            .resourceId(R.drawable.bg3)
                            .build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bg::bitmap);

            timePresenter.startTimer();
            gasInfoPresenter.getGasLevels();
            gasInfoPresenter.worstSafetyLevel();
        });
    }

    @Inject
    public void setPresenter(GasInfoPresenter presenter) {
        gasInfoPresenter = presenter;
    }

    @Inject
    public void setPresenter(TimePresenter presenter) {
        timePresenter = presenter;
    }

    // make sure presenters survive configuration changes
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        List<BasePresenter> presenters  = new ArrayList<>();
        presenters.add(gasInfoPresenter);
        presenters.add(timePresenter);
        return presenters;
    }

    @Override
    public void startAlarm(String gasName) {
        if(!MyApplication.alarmDismissed) {
            Intent intent = new Intent(this, AlarmActivity.class);
            intent.putExtra("gasName", gasName);
            startActivity(intent);
        }
    }

    // switch app theme depending on gas levels
    @Override
    public void switchTheme(int safetyLevel) {
        if (styles[safetyLevel] != style) {
            style = styles[safetyLevel];
            recreate();
        }
    }

    @Override
    public void setSafetyLabel(String label) {
        $(this, R.id.safetyLevel).text(label);
    }

    // displays gas levels
    @Override
    public void addGas(final String name, String label, String current, String safe, String danger) {
        $ parent = $(this, R.id.gasInfo);
        $ gasInfo = $.inflate(this, R.layout.gas_info, parent)
                        .click(v -> new AlertDialog.Builder(MainActivity.this) // displays gas info on click
                                .setTitle(name+" Info")
                                .setMessage(gasInfoPresenter.getInfo(name.toLowerCase()))
                                .setPositiveButton(android.R.string.yes, (d,w) -> {})
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show());

        gasInfo.find(R.id.gasName).text(name);
        gasInfo.find(R.id.gas_safety_label).text(label);
        gasInfo.find(R.id.currentLevel).text(String.format("%s PPM", current));
        gasInfo.find(R.id.recommendedLevel).text(String.format("%s PPM", safe));
        gasInfo.find(R.id.dangerLevel).text(String.format("%s PPM", danger));

        parent.append(gasInfo);
    }

    // displays temperature
    @Override
    public void displayTemperature(int t) {
        $(this, R.id.temperature).text(String.format("%d ℃", t));
    }

    @Override
    public void setTime(String timeStr) {
        $(this, R.id.current_time).text(timeStr);
    }

    // inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register:
                SharedPreferences prefs = getSharedPreferences(getString(R.string.prefName), Context.MODE_PRIVATE);
                Intent intent;

                if (prefs.getBoolean(getString(R.string.isRegistered), false))
                    intent= new Intent(this, UnsubscribeActivity.class);
                else
                    intent= new Intent(this, AddSensorActivity.class);

                intent.putExtra("style", style);
                startActivity(intent);
                break;
        }

        return true;
    }
}