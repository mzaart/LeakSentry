package com.mzaart.leaksentry.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.mzaart.leaksentry.MyApplication;
import com.mzaart.leaksentry.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mzaart.leaksentry.utils.BitmapLoader;
import com.mzaart.leaksentry.utils.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(context.getString(R.string.prefName), Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Resources getResources() {
        return context.getResources();
    }

    @Singleton
    @Provides
    public ResourceProvider getResourceProvider() {
        return new ResourceProvider(context.getResources(), context.getPackageName());
    }

    @Provides
    public FirebaseInstanceId getFirebaseInstanceId() {
        return FirebaseInstanceId.getInstance();
    }
    
    @Provides
    public BitmapLoader getBitmapLoader() {
        return new BitmapLoader(context);
    }
}
