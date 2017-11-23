package com.mzaart.leaksentry.mvp.gasInfo;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mzaart.leaksentry.utils.BitmapLoader;
import com.mzaart.leaksentry.utils.ResourceProvider;

import java.util.*;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GasInfoPresenter extends GasInfoContract.ViewActions {

    @Inject
    public ResourceProvider res;

    @Inject
    public SharedPreferences prefs;

    @Inject
    public BitmapLoader bitmapLoader;

    private Set<Gas> gases;

    @Override
    public String getInfo(String gasName) {
        return res.readText(gasName);
    }

    @Override
    public void getGasLevels() {
        setupGases();
        for (Gas gas : gases) {
            if (isViewAttached()) {
                getView().addGas(gas.name,
                        gas.getSafetyLabel(),
                        "Current Level: " + gas.currentLevel,
                        "Recommended Level: " + gas.recommendedLevel,
                        "Danger Level: " + gas.dangerLevel
                );
            }
        }
    }

    @Override
    public void worstSafetyLevel() {
        setupGases();
        Gas worst = gases.stream()
                .reduce(new Gas(), (g1, g2) -> g1.compareTo(g2) > 0 ? g1 : g2);

        getView().switchTheme(worst.getSafetyLevel());
        getView().setSafetyLabel(worst.getSafetyLabel());

        if (worst.isDangerous()) {
            getView().startAlarm(worst.name);
        }
    }

    @Override
    public void getTemperature() {
        int t = prefs.getInt(res.getString("temperature"), -1);

        if (isViewAttached()) {
            getView().displayTemperature(t);
        }
    }

    @Override
    public void loadBackground(int w, int h, int resId) {
        Single.defer(() -> Single.just(bitmapLoader.decodeSampledBitmapFromResource(resId, w, h)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> {
                    if (isViewAttached()) {
                        getView().displayBackground(b);
                    }
                });
    }

    private void setupGases() {
        if (gases == null) {
            Gson gson = new Gson();
            gases = new HashSet<>();
            prefs.getStringSet("gases", new HashSet<>())
                    .forEach(json -> gases.add(gson.fromJson(json, Gas.class)));
        }
    }
}
