package com.mzaart.leaksentry.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.leaksentry.alarm.AlarmContract;
import com.mzaart.leaksentry.aquery.$;
import static com.mzaart.leaksentry.aquery.Constructors.*;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;

import com.mzaart.leaksentry.R;

import javax.inject.Inject;

import com.mzaart.leaksentry.alarm.AlarmPresenter;

import com.mzaart.leaksentry.dagger.components.DaggerPresenterComponent;

public class AlarmActivity extends AppCompatActivity implements AlarmContract.AlarmView {

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

        $(this, R.id.dangerousGas).text(gasName.toUpperCase()+ " levels are Dangerous!");

        // play alarm
        player = MediaPlayer.create(this, R.raw.alarm);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(true);
        player.start();

        // set up presenter
        presenter = (AlarmContract.ViewActions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            // inject presenter
            DaggerPresenterComponent.builder()
                    .presenterModule(new PresenterModule())
                    .build()
                    .inject(this);
        }
        presenter.attachView(this);

        // inject presenter dependencies
        ((MyApplication) getApplication()).getComponent().inject((AlarmPresenter) presenter);

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
        $.inflate(this, R.layout.dos, $(this, R.id.dos), true)
                .find(R.id.do_item)
                .text(action);
    }

    @Override
    public void addDonts(String action) {
        $.inflate(this, R.layout.donts, $(this, R.id.donts), true)
                .find(R.id.dont_item)
                .text(action);
    }
    
    @Override
    protected void onDestroy() {
        player.stop();
        MyApplication.alarmDismissed = true;
        super.onDestroy();
    }
}