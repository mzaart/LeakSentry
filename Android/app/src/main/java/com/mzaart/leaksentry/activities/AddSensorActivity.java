package com.mzaart.leaksentry.activities;

import android.content.Intent;
import android.os.Bundle;

import com.mzaart.leaksentry.mvp.addSensor.AddSensorContract;
import com.mzaart.aquery.$;
import static com.mzaart.aquery.Constructors.*;

import com.mzaart.leaksentry.R;

import javax.inject.Inject;

import com.mzaart.leaksentry.mvp.addSensor.AddSensorPresenter;

public class AddSensorActivity extends BaseActivity implements AddSensorContract.AddSensorView {

    public AddSensorContract.ViewActions presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set theme
        Intent intent = getIntent();
        setTheme(intent.getIntExtra("style", R.style.Safe));

        setContentView(R.layout.activity_add_sensor);

        // setup presenter
        presenter = (AddSensorContract.ViewActions) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            getPresenterComponent().inject(this);
            getAppComponent().inject((AddSensorPresenter) presenter);
        }
        presenter.attachView(this);

        $(this).ready(() -> {
            $(this, R.id.addSensor).click(v -> { // subscribe
                int id = Integer.parseInt($(this, R.id.sensorId).text());
                if (id <= 0) {
                    $.toast(this, getString(R.string.invalidId));
                } else {
                    presenter.subscribe(id);
                }
            });
        });
    }

    @Inject
    public void setPresenter(AddSensorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    // inform user on subscription result
    @Override
    public void confirmSubscription(boolean isSuccessful) {
        if (isSuccessful) { // go back to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("message", getString(R.string.onSubscribeSuccess));
            startActivity(intent);
        } else {
            $.toast(this, getString(R.string.onSubscribeFail));
        }
    }
}
