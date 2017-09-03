package com.mzaart.leaksentry.utils.rxUtils.bitmapObservableSource;

import android.graphics.Bitmap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import com.mzaart.leaksentry.utils.ImageUtils;

class ResourceBitmapObservableSource extends BitmapObservableSource {

    @Override
    public void subscribe(@NonNull Observer<? super Bitmap> observer) {
        try {
            // create bitmap
            Bitmap b = ImageUtils.decodeSampledBitmapFromResource(res, resId, reqWidth, reqHeight);
            observer.onNext(b);
            observer.onComplete();
        } catch (Exception e) {
            observer.onError(e);
        }
    }
}
