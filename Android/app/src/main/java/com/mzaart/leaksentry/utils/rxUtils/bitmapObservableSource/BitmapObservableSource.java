package com.mzaart.leaksentry.utils.rxUtils.bitmapObservableSource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import io.reactivex.ObservableSource;

public abstract class BitmapObservableSource implements ObservableSource<Bitmap> {

    protected Context context;
    protected int reqWidth;
    protected int reqHeight;

    protected Uri imgUri;
    protected String name;
    protected Resources res;
    protected int resId;


    public static class Builder {
        private final Context context;
        private final int reqWidth;
        private final int reqHeight;

        private Uri imgUri;
        private String name;
        private Resources res;
        private int resId;

        public Builder(Context context, int reqWidth, int reqHeight) {
            this.context = context;
            this.reqWidth = reqWidth;
            this.reqHeight = reqHeight;
        }

        public Builder uri(@NonNull Uri imgUri) {
            this.imgUri = imgUri;
            return this;
        }

        public Builder name(@NonNull String name) {
            this.name = name;
            return this;
        }

        public Builder resources(@NonNull Resources res) {
            this.res = res;
            return this;
        }

        public Builder resourceId(int resId) {
            this.resId = resId;
            return this;
        }

        public BitmapObservableSource build() {
            // bitmap from resource
            if(res != null) {
                BitmapObservableSource src = new ResourceBitmapObservableSource();
                src.context = context;
                src.reqWidth = reqWidth;
                src.reqHeight = reqHeight;
                src.res = res;
                src.resId = resId;
                return src;
            }

            // bitmap from cache
            if(name != null) {
                BitmapObservableSource src = new CacheBitmapObservableSource();
                src.context = context;
                src.reqWidth = reqWidth;
                src.reqHeight = reqHeight;
                src.name = name;
                return src;
            }

            // bitmap from uri
            if(imgUri != null) {
                BitmapObservableSource src = new UriBitmapObservableSource();
                src.context = context;
                src.reqWidth = reqWidth;
                src.reqHeight = reqHeight;
                src.imgUri = imgUri;
                return src;
            }

            throw new IllegalArgumentException();
        }
    }
}
