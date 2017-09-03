package com.mzaart.leaksentry.unsubscribeSensor;

import com.mzaart.leaksentry.base.BasePresenter;

public interface UnsubscribeContract {

    public static abstract class ViewActions extends BasePresenter<View> {
        public abstract void unSubscribe();
        public abstract void getSensorId();
    }

    public interface View {
        void confirm(boolean succeeded);
        void displaySensorId(int id);
    }
}
