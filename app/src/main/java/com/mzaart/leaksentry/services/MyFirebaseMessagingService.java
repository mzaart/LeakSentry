package com.mzaart.leaksentry.services;

import android.content.Intent;
import android.content.SharedPreferences;

import com.mzaart.leaksentry.activities.AlarmActivity;
import com.mzaart.leaksentry.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.mzaart.leaksentry.gasInfo.Gas;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data =  remoteMessage.getData();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefName), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // get message mode
        if (data.get("mode").equals("temperature")) {
            editor.putInt(getString(R.string.temperature), Integer.parseInt(data.get("temperature")));
        } else if(data.get("mode").equals("gas")) {
            Gson gson = new Gson();
            Set<String> gases = gson.fromJson(data.get("jsonGas"), HashSet.class);
            editor.putStringSet(getString(R.string.gases), gases);

            // check for dangerous levels
            for (String jsonGas: gases) {
                Gas gas = gson.fromJson(jsonGas, Gas.class);

                if (gas.isDangerous()) {
                    Intent intent = new Intent(this, AlarmActivity.class);
                    intent.putExtra("gasName", gas.name);
                    startActivity(intent);
                    break;
                }
            }
        }

        editor.apply();
    }
}
