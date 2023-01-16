package com.example.clockapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.time.ZoneId;
import java.util.ArrayList;

public class TimeZoneActivity extends AppCompatActivity {
    private RecyclerView selectTimeZones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone);

        //get all time zones
        ArrayList<TimeZone> zones = Utils.getInstance().getAllZones();

        //Set adapter for rec view
        TimeZonesRecViewAdapter adapter = new TimeZonesRecViewAdapter(this);
        selectTimeZones = findViewById(R.id.selectTimeZonesRecView);
        selectTimeZones.setAdapter(adapter);
        selectTimeZones.setLayoutManager(new LinearLayoutManager(this));

        adapter.setZones(zones);

    }
}