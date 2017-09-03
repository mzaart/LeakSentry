package com.mzaart.leaksentry.gasInfo;

import com.mzaart.leaksentry.base.BasePresenter;

public abstract class GasInfoContract {

    public abstract static class ViewActions extends BasePresenter<GasInfoView> {
        public abstract String getInfo(String gasName);
        public abstract void getGasLevels();
        public abstract void worstSafetyLevel();
        public abstract void getTemperature();
    }

    public interface GasInfoView {
        void startAlarm(String gasName);
        void switchTheme(int safetyLevel);
        void setSafetyLabel(String label);
        void addGas(String name, String label, String current, String safe, String danger);
        void displayTemperature(int t);
    }
}
