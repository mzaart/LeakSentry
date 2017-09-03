package com.mzaart.leaksentry.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import com.mzaart.leaksentry.R;
import com.mzaart.leaksentry.api.SensorAPI;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private Context context;

    public ApiModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SensorAPI getAPI() {
        String baseUrl = context.getString(R.string.baseUrl);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(SensorAPI.class);
    }
}
