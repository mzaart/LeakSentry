package com.mzaart.leaksentry.mvp.addSensor;

import com.mzaart.leaksentry.mvp.BasePresenter;

public abstract class AddSensorContract {

    public static abstract class ViewActions extends BasePresenter<AddSensorView> {
        public abstract void subscribe(int sensorID);
    }

    public interface AddSensorView {
        void confirmSubscription(boolean isSuccessful);
    }
}
