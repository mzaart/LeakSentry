package com.mzaart.leaksentry.dagger.components;

import com.mzaart.leaksentry.activities.AddSensorActivity;
import com.mzaart.leaksentry.activities.AlarmActivity;
import com.mzaart.leaksentry.activities.MainActivity;
import com.mzaart.leaksentry.activities.UnsubscribeActivity;

import javax.inject.Singleton;
import dagger.Component;
import com.mzaart.leaksentry.dagger.modules.PresenterModule;

@Singleton
@Component(modules = {
        PresenterModule.class
})
public interface PresenterComponent {
    void inject(MainActivity activity);
    void inject(AddSensorActivity activity);
    void inject(AlarmActivity activity);
    void inject(UnsubscribeActivity activity);
}
