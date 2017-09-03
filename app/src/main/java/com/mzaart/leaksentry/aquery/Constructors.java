package com.mzaart.leaksentry.aquery;

import android.app.Activity;
import android.view.View;

public class Constructors {

    public static  $ $(View v) {
        return new $(v);
    }

    public static $ $(Activity activity) {
        return new $(activity);
    }

    public static $ $(Activity activity, int id) {
        return new $(activity, id);
    }

    public static $ $(View view, int id) {
        return new $(view, id);
    }

    public static $ $($ aquery, int id) {
        return new $(aquery, id);
    }
}
