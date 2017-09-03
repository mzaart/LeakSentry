package com.mzaart.leaksentry.gasInfo;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mzaart.leaksentry.utils.ResourceProvider;

import java.util.*;

import javax.inject.Inject;

import io.reactivex.Observable;

import static junit.framework.Assert.fail;

public class GasInfoPresenter extends GasInfoContract.ViewActions {

    @Inject
    public ResourceProvider res;

    @Inject
    public SharedPreferences prefs;

    public Set<Gas> gases;

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
        Gas worst = Observable.fromIterable(gases)
                .reduce(new Gas(), (g1, g2) -> g1.compareTo(g2) > 0 ? g1 : g2)
                .blockingGet();

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

    private void setupGases() {
        if (gases == null) {
            Gson gson = new Gson();

            gases = new HashSet<>();
            for (String json: prefs.getStringSet("gases", new HashSet<>()))
                gases.add(gson.fromJson(json, Gas.class));
        }
    }
}
