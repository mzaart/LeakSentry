package com.mzaart.leaksentry.api;

import com.mzaart.leaksentry.mvp.addSensor.Response;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SensorAPI {

    @FormUrlEncoded
    @POST("SubscribeTo")
    Single<Response> subscribeTo(@Field("notificationId") String notificationToken,
                                 @Field("sensorId") int sensorId);

    @FormUrlEncoded
    @POST("unSubscribeFrom")
    Single<Response> unSubscribeFrom(@Field("notificationId") String notificationToken,
                                 @Field("sensorId") int sensorId);
}


