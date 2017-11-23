package com.mzaart.leaksentry.mvp.addSensor;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mzaart.leaksentry.api.SensorAPI;
import com.mzaart.leaksentry.utils.ResourceProvider;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddSensorPresenter extends AddSensorContract.ViewActions {

    @Inject
    public ResourceProvider res;

    @Inject
    public SharedPreferences prefs;

    @Inject
    public SensorAPI api;
    @Inject
    public FirebaseInstanceId firebaseInstanceId;

    @Override
    public void subscribe(int sensorID) {
        api.subscribeTo(firebaseInstanceId.getToken(), sensorID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( r -> {
                    if (r.isSuccessful()) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(res.getString("isRegistered"), true);
                        editor.apply();
                    }

                    if (isViewAttached())
                        getView().confirmSubscription(r.isSuccessful());
                });
    }
}
