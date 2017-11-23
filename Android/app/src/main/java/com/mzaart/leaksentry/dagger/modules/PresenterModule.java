package com.mzaart.leaksentry.dagger.modules;

import com.mzaart.leaksentry.mvp.time.TimePresenter;
import com.mzaart.leaksentry.mvp.unsubscribeSensor.UnsubscribePresenter;
import com.mzaart.leaksentry.mvp.addSensor.AddSensorPresenter;
import com.mzaart.leaksentry.mvp.alarm.AlarmPresenter;
import dagger.Module;
import dagger.Provides;
import com.mzaart.leaksentry.mvp.gasInfo.GasInfoPresenter;

@Module
public class PresenterModule {

    @Provides
    public GasInfoPresenter getGasInfoPresenter() {
        return new GasInfoPresenter();
    }

    @Provides
    public TimePresenter getTimePresenter() {
        return new TimePresenter();
    }

    @Provides
    public AddSensorPresenter getAddSensorPresenter() {
        return new AddSensorPresenter();
    }

    @Provides
    public AlarmPresenter getAlarmPresenter() {
        return new AlarmPresenter();
    }

    @Provides
    public UnsubscribePresenter getUnsubscribePresenter() {
        return new UnsubscribePresenter();
    }
}
