package com.mzaart.leaksentry.alarm;

import com.mzaart.leaksentry.base.BasePresenter;

public abstract class AlarmContract {

    public static abstract class ViewActions extends BasePresenter<AlarmView> {
        public abstract void getDos(String gasName);
        public abstract void getDonts(String gasName);
    }

    public interface AlarmView {
        void addDos(String action);
        void addDonts(String action);
    }
}
