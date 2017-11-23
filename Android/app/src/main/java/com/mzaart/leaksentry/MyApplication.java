package com.mzaart.leaksentry;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import com.mzaart.leaksentry.dagger.components.AppComponent;
import com.mzaart.leaksentry.dagger.components.DaggerAppComponent;
import com.mzaart.leaksentry.dagger.modules.ApiModule;
import com.mzaart.leaksentry.dagger.modules.AppModule;

public class MyApplication extends Application {

    public static boolean isRegistered;
    public static boolean alarmDismissed = false;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefName), Context.MODE_PRIVATE);

        // check if this is first launch
        if(prefs.getBoolean(getString(R.string.isFirstLaunch), true)) {
            isRegistered = false;
            SharedPreferences.Editor editor = prefs.edit();

            // create keys-value pairs
            editor.putBoolean(getString(R.string.isRegistered), false);

            String jsonGas1 = "{\"name\":\"LPG\",\"currentLevel\":0,\"recommendedLevel\":400,\"cautionLevel\":600,\"warningLevel\":800,\"dangerLevel\":1000}";
            String jsonGas2 = "{\"name\":\"CO\",\"currentLevel\":10,\"recommendedLevel\":20,\"cautionLevel\":50,\"warningLevel\":80,\"dangerLevel\":100}";

            Set<String> gases = new HashSet<>();
            gases.add(jsonGas1);
            gases.add(jsonGas2);
            editor.putStringSet(getString(R.string.gases), gases);

            editor.apply();
        } else {
            isRegistered = prefs.getBoolean(getString(R.string.isRegistered), false);
        }
    }
}
