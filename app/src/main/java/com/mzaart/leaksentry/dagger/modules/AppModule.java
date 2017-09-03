package com.mzaart.leaksentry.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.leaksentry.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mzaart.leaksentry.utils.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApplication app;

    public AppModule(MyApplication app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public SharedPreferences getSharedPreferences() {
        return app.getSharedPreferences(app.getString(R.string.prefName), Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Resources getResources() {
        return app.getResources();
    }

    @Singleton
    @Provides
    public ResourceProvider getResourceProvider() {
        return new ResourceProvider(app.getResources(), app.getPackageName());
    }

    @Provides
    public FirebaseInstanceId getFirebaseInstanceId() {
        return FirebaseInstanceId.getInstance();
    }
}
