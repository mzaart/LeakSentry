package com.mzaart.leaksentry.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.aquery.$;
import static com.mzaart.aquery.Constructors.*;
import com.mzaart.leaksentry.mvp.BasePresenter;
import com.mzaart.leaksentry.mvp.gasInfo.GasInfoContract;
import com.mzaart.leaksentry.mvp.gasInfo.GasInfoPresenter;
import com.mzaart.leaksentry.mvp.time.TimeContract;
import com.mzaart.leaksentry.mvp.time.TimePresenter;

import com.mzaart.leaksentry.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements GasInfoContract.GasInfoView,
        TimeContract.TimeView {

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
            getPresenterComponent().inject(this);

            getAppComponent().inject((GasInfoPresenter) gasInfoPresenter);
        } else {
            gasInfoPresenter = (GasInfoContract.ViewActions) presenters.get(0);
            timePresenter = (TimeContract.ViewActions) presenters.get(1);
        }

        gasInfoPresenter.attachView(this);
        timePresenter.attachView(this);

        // set date
        $(this, R.id.current_time).text(SimpleDateFormat.getTimeInstance().format(new Date()));
        $(this, R.id.current_day).text(SimpleDateFormat.getDateInstance().format(new Date()));

        $(this).ready(() -> {
            // set background image
            $ bg = $(this, R.id.bg);
            gasInfoPresenter.loadBackground(bg.width(), bg.height(), R.drawable.bg3);

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

    @Override
    public void displayTemperature(int t) {
        $(this, R.id.temperature).text(String.format("%d ℃", t));
    }

    @Override
    public void displayBackground(Bitmap b) {
        $(this, R.id.bg).bitmap(b);
    }

    @Override
    public void setTime(String timeStr) {
        $(this, R.id.current_time).text(timeStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register:
                SharedPreferences prefs = getSharedPreferences(getString(R.string.prefName), Context.MODE_PRIVATE);
                Intent intent;

                if (prefs.getBoolean(getString(R.string.isRegistered), false)) {
                    intent = new Intent(this, UnsubscribeActivity.class);
                }
                else {
                    intent = new Intent(this, AddSensorActivity.class);
                }

                intent.putExtra("style", style);
                startActivity(intent);
                break;
        }

        return true;
    }
}