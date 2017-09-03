package com.mzaart.leaksentry.utils.rxUtils.bitmapObservableSource;

import android.app.Activity;
import android.graphics.Bitmap;

import com.mzaart.leaksentry.utils.ImageUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

public class UriBitmapObservableSource extends BitmapObservableSource {

    @Override
    public void subscribe(@NonNull Observer<? super Bitmap> observer) {
        try {
            Bitmap b = ImageUtils.decodeSampledBitmapFromInputStream(context, imgUri, reqWidth, reqHeight);
            ImageUtils.rotateImage((Activity) context, imgUri, b);
            observer.onNext(b);
            observer.onComplete();
        } catch (Exception e) {
            observer.onError(e);
        }
    }
}
