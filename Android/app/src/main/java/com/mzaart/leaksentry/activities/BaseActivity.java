package com.mzaart.leaksentry.activities;

import android.support.v7.app.AppCompatActivity;

import com.mzaart.leaksentry.dagger.components.AppComponent;
import com.mzaart.leaksentry.dagger.components.DaggerAppComponent;
import com.mzaart.leaksentry.dagger.components.DaggerPresenterComponent;
import com.mzaart.leaksentry.dagger.components.PresenterComponent;
import com.mzaart.leaksentry.dagger.modules.ApiModule;
import com.mzaart.leaksentry.dagger.modules.AppModule;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;

public class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .apiModule(new ApiModule(this))
                    .build();
    }

    protected PresenterComponent getPresenterComponent() {
        return DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule())
                .build();
    }
}
