package com.mzaart.leaksentry.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.leaksentry.mvp.alarm.AlarmContract;
import com.mzaart.aquery.$;
import static com.mzaart.aquery.Constructors.*;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;

import com.mzaart.leaksentry.R;

import javax.inject.Inject;

import com.mzaart.leaksentry.mvp.alarm.AlarmPresenter;

import com.mzaart.leaksentry.dagger.components.DaggerPresenterComponent;

public class AlarmActivity extends BaseActivity implements AlarmContract.AlarmView {

    public AlarmContract.ViewActions presenter;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Dangerous);
        setContentView(R.layout.activity_alarm);

        // get gas name
        Intent intent = getIntent();
        String gasName = intent.getStringExtra("gasName");

        $(this, R.id.dangerousGas).text(gasName.toUpperCase() + " levels are Dangerous!");

        // play alarm
        player = MediaPlayer.create(this, R.raw.alarm);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(true);
        player.start();

        // set up presenter
        presenter = (AlarmContract.ViewActions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            // inject presenter
            getPresenterComponent().inject(this);
        }
        presenter.attachView(this);

        // inject presenter dependencies
        getAppComponent().inject((AlarmPresenter) presenter);

        $(this).ready(() -> {
            // fetch instructions
            presenter.getDos(gasName);
            presenter.getDonts(gasName);
        });
    }

    @Inject
    public void setPresenter(AlarmPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    // display instructions
    @Override
    public void addDos(String action) {
        $ parent = $(this, R.id.dos);
        $ item = $.inflate(this, R.layout.dos, parent);
        item.find(R.id.do_item).text(action);
        parent.append(item);
    }

    @Override
    public void addDonts(String action) {
        $ parent = $(this, R.id.donts);
        $ item = $.inflate(this, R.layout.donts, parent);
        item.find(R.id.dont_item).text(action);
        parent.append(item);
    }
    
    @Override
    protected void onDestroy() {
        player.stop();
        MyApplication.alarmDismissed = true;
        super.onDestroy();
    }
}
