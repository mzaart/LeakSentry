package com.mzaart.leaksentry.mvp.gasInfo;

import android.graphics.Bitmap;

import com.mzaart.leaksentry.mvp.BasePresenter;

public abstract class GasInfoContract {

    public abstract static class ViewActions extends BasePresenter<GasInfoView> {
        public abstract String getInfo(String gasName);
        public abstract void getGasLevels();
        public abstract void worstSafetyLevel();
        public abstract void getTemperature();
        public abstract void loadBackground(int w, int h, int resId);
    }

    public interface GasInfoView {
        void startAlarm(String gasName);
        void switchTheme(int safetyLevel);
        void setSafetyLabel(String label);
        void addGas(String name, String label, String current, String safe, String danger);
        void displayTemperature(int t);
        void displayBackground(Bitmap b);
    }
}
