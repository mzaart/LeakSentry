package com.mzaart.leaksentry.dagger.components;

import com.mzaart.leaksentry.dagger.modules.AppModule;
import com.mzaart.leaksentry.mvp.unsubscribeSensor.UnsubscribePresenter;

import javax.inject.Singleton;
import com.mzaart.leaksentry.mvp.addSensor.AddSensorPresenter;
import com.mzaart.leaksentry.mvp.alarm.AlarmPresenter;
import dagger.Component;
import com.mzaart.leaksentry.dagger.modules.ApiModule;
import com.mzaart.leaksentry.mvp.gasInfo.GasInfoPresenter;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppComponent {
    void inject(AddSensorPresenter presenter);
    void inject(AlarmPresenter presenter);
    void inject(GasInfoPresenter presenter);
    void inject(UnsubscribePresenter presenter);
}