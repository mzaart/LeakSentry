package com.mzaart.leaksentry.mvp.alarm;

import com.mzaart.leaksentry.utils.ResourceProvider;
import com.mzaart.leaksentry.utils.rxUtils.BufferedReaderObservableSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlarmPresenter extends AlarmContract.ViewActions {

    @Inject
    public ResourceProvider res;

    @Override
    public void getDos(String gasName) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(res.openRawResource(gasName.toLowerCase() + "_dos")));

        Observable.defer(() -> new BufferedReaderObservableSource(reader))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(line -> {
                    if (isViewAttached()) {
                        getView().addDos(line);
                    }
                });
    }

    @Override
    public void getDonts(String gasName) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                res.openRawResource(gasName.toLowerCase() + "_donts")));

        Observable.defer(() -> new BufferedReaderObservableSource(reader))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(line -> {
                    if (isViewAttached()) {
                        getView().addDonts(line);
                    }
                });
    }
}
