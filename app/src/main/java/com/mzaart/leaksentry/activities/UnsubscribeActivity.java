package com.mzaart.leaksentry.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mzaart.leaksentry.MyApplication;
import static com.mzaart.leaksentry.aquery.Constructors.*;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;
import com.mzaart.leaksentry.unsubscribeSensor.UnsubscribePresenter;

import com.mzaart.leaksentry.R;

import javax.inject.Inject;

import com.mzaart.leaksentry.dagger.components.DaggerPresenterComponent;

import com.mzaart.leaksentry.unsubscribeSensor.UnsubscribeContract;

public class UnsubscribeActivity extends AppCompatActivity implements UnsubscribeContract.View {

    UnsubscribeContract.ViewActions presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set style
        Intent intent = getIntent();
        setTheme(intent.getIntExtra("style", R.style.Safe));

        setContentView(R.layout.activity_unsubscribe);

        // setup presenter
        presenter = (UnsubscribeContract.ViewActions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            DaggerPresenterComponent.builder()
                    .presenterModule(new PresenterModule())
                    .build()
                    .inject(this);
            ((MyApplication) getApplication()).getComponent().inject((UnsubscribePresenter) presenter);
        }
        presenter.attachView(this);

        $(this).ready(() -> {
            $(this, R.id.unsubscribe).click(v -> presenter.unSubscribe());
            presenter.getSensorId();
        });
    }

    @Inject
    public void setPresenter(UnsubscribePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    // inform user about unsubscription result
    @Override
    public void confirm(boolean succeeded) {
        Intent intent = new Intent(this, MainActivity.class);

        if (succeeded)
            intent.putExtra("message", "Successfully unsubscribed from sensor.");
        else
            intent.putExtra("message", "Failed to unsubscribe from sensor.");

        startActivity(intent);
    }

    @Override
    public void displaySensorId(int id) {
        $(this, R.id.sensorId).text(String.valueOf(id));
    }
}
