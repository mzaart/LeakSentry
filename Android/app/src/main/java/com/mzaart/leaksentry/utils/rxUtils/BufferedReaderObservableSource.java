package com.mzaart.leaksentry.utils.rxUtils;

import java.io.BufferedReader;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;


public class BufferedReaderObservableSource implements ObservableSource<String> {

    private BufferedReader reader;

    public BufferedReaderObservableSource(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void subscribe(@NonNull Observer<? super String> observer) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                observer.onNext(line);
            }
            observer.onComplete();
        } catch (Exception e) {
            observer.onError(e);
        }
    }
}
