package com.mzaart.leaksentry.aquery;

public class Utils {

    public static void requireNotNull(Object... params) {
        for (Object o : params) {
            if (o == null)
                throw new IllegalArgumentException("Parameter can't be null");
        }
    }
}
