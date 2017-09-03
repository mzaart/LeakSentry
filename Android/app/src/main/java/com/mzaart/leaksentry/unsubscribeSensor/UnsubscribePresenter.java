package com.mzaart.leaksentry.unsubscribeSensor;


import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;

import javax.inject.Inject;

import com.mzaart.leaksentry.api.SensorAPI;
import com.mzaart.leaksentry.utils.ResourceProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UnsubscribePresenter extends UnsubscribeContract.ViewActions {

    @Inject
    public SharedPreferences prefs;

    @Inject
    public ResourceProvider res;

    @Inject
    public SensorAPI api;

    @Inject
    public FirebaseInstanceId firebaseInstanceId;

    @Override
    public void unSubscribe() {
        try {
            api.unSubscribeFrom(firebaseInstanceId.getToken(), prefs.getInt("sensorId", -1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( r -> {
                        if (r.isSuccessful()) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(res.getString("isRegistered"), false);
                            editor.apply();
                        }

                        if (isViewAttached())
                            getView().confirm(r.isSuccessful());
                    });
        } catch (NullPointerException e) {
            if (isViewAttached())
                getView().confirm(false);
        }
    }

    @Override
    public void getSensorId() {
        getView().displaySensorId(prefs.getInt("sensorId", -1));
    }
}
