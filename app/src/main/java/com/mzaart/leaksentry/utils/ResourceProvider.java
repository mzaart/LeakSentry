package com.mzaart.leaksentry.utils;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceProvider {

    private Resources res;
    private String packageName;

    public ResourceProvider(Resources res, String packageName) {
        this.res = res;
        this.packageName = packageName;
    }

    public String getString(String id) {
        return res.getString(res.getIdentifier(id, "string", packageName));
    }

    public int getInteger(String id) {
        return res.getInteger(res.getIdentifier(id, "integer", packageName));
    }

    public boolean getBoolean(String id) {
        return res.getBoolean(res.getIdentifier(id, "bool", packageName));
    }

    public InputStream openRawResource(String id) {
        return res.openRawResource(res.getIdentifier(id, "raw", packageName));
    }

    public String readText(String id) {
        // get buffered reader
        InputStreamReader inputStreamReader = new InputStreamReader(openRawResource(id));
        BufferedReader reader = new BufferedReader(inputStreamReader);

        // build string
        StringBuilder string = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                string.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.toString();
    }
}
