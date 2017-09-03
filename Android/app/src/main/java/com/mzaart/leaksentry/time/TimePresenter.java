package com.mzaart.leaksentry.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TimePresenter extends TimeContract.ViewActions {

    private static final int PERIOD = 60000; // 1 minute

    @Override
    public void startTimer() {
        Observable.timer(PERIOD, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    if (isViewAttached())
                        getView().setTime(new SimpleDateFormat("hh:mm a").format(new Date()));
                });
    }
}
