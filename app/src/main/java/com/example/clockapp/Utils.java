package com.example.clockapp;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Utils {
    private static Utils instance;
    private ArrayList<TimeZone> allZones;
    private ArrayList<TimeZone> displayZones;

    private Utils() {
        if (allZones == null) {
            allZones = new ArrayList<>();
            for(String zone : ZoneId.getAvailableZoneIds()) {
                allZones.add(new TimeZone(zone));
            }
            Collections.sort(allZones, new Comparator<TimeZone>() {
                @Override
                public int compare(TimeZone timeZone, TimeZone t1) {
                    return timeZone.getName().compareTo(t1.getName());
                }
            });
        }
        if (displayZones == null) {
            displayZones = new ArrayList<>();
        }
    }

    public ArrayList<TimeZone> getAllZones() {
        return allZones;
    }

    public ArrayList<TimeZone> getDisplayZones() {
        return displayZones;
    }

    public static Utils getInstance() {
        if (instance == null){
            instance = new Utils();
        }
        return instance;
    }

    public boolean addToDisplayZones(TimeZone zone) {
        return displayZones.add(zone);
    }
}
