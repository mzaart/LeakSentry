package com.mzaart.leaksentry.mvp.unsubscribeSensor;

import com.mzaart.leaksentry.mvp.BasePresenter;

public interface UnsubscribeContract {

    abstract class ViewActions extends BasePresenter<View> {
        public abstract void unSubscribe();
        public abstract void getSensorId();
    }

    interface View {
        void confirm(boolean succeeded);
        void displaySensorId(int id);
    }
}
