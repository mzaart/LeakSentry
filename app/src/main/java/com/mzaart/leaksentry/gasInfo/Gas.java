package com.mzaart.leaksentry.gasInfo;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;


public class Gas implements Comparable<Gas> {

    public String name;
    
    public int currentLevel;
    public int recommendedLevel;
    public int cautionLevel;
    public int warningLevel;
    public int dangerLevel;

    public boolean isDangerous() {
        return currentLevel > warningLevel;
    }

    public String getSafetyLabel() {
        if(currentLevel <= recommendedLevel) {
            return "Safe";
        } else if(currentLevel <= cautionLevel) {
            return "Moderate";
        } else if(currentLevel <= warningLevel) {
            return "Dangerous";
        } else {
            return "Lethal";
        }
    }

    public Integer getSafetyLevel() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Safe", 0);
        map.put("Moderate", 1);
        map.put("Dangerous", 2);
        map.put("Lethal", 3);

        return map.get(getSafetyLabel());
    }

    @Override
    public int compareTo(@Nullable Gas o) {
        return getSafetyLevel().compareTo(o.getSafetyLevel());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Gas && name.equals(((Gas) o).name);
    }
}

