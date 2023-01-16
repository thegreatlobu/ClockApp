package com.example.clockapp;

import static com.example.clockapp.TimeZonesRecViewAdapter.TIME_ZONE_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView txtCurrentTime, txtCurrentDate;
    private FloatingActionButton btnAddTimeZone, btnStartTimerActivity;
    private RecyclerView txtDisplayTimeZone;
    private boolean stopThreadExecution = false;

    private Handler mainHandler = new Handler();

    private void startThread(TimeThread thread) {
        stopThreadExecution = false;
        new Thread(thread).start();
    }

    private void stopThread() {
        stopThreadExecution = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String zoneId = ZoneId.systemDefault().toString();
        initViews();

        //btn navigates user to selectTimeZoneActivity
        btnAddTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimeZoneActivity.class);
                startActivity(intent);
            }
        });

        btnStartTimerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        //set adapter for display time zone
        DisplayTimeZoneRecViewAdapter adapter = new DisplayTimeZoneRecViewAdapter(this);
        txtDisplayTimeZone.setAdapter(adapter);
        txtDisplayTimeZone.setLayoutManager(new LinearLayoutManager(this));
        adapter.setZones(Utils.getInstance().getDisplayZones());


        //Start timer thread to get current time and date
        TimeThread time = new TimeThread(zoneId);
        startThread(time);
    }

    private void initViews(){
        txtCurrentDate = findViewById(R.id.txtCurrentDate);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        btnAddTimeZone = findViewById(R.id.btnAddTimeZone);
        txtDisplayTimeZone = findViewById(R.id.txtDisplayTimeZone);
        btnStartTimerActivity = findViewById(R.id.btnStartTimerActivity);
    }

    class TimeThread implements Runnable{
        private String zone;
        public TimeThread(String zone) {
            this.zone = zone;
        }

        //Handler pushes thread into msg queue every second and updates time and date
        @Override
        public void run() {
            //stop the thread when activity is destroyed
            if (stopThreadExecution) {
                mainHandler.removeCallbacks(this);
                return;
            }

            mainHandler.postDelayed(this, 1000);
            LocalDate ld = LocalDate.now(ZoneId.of(zone));
            LocalTime lt = LocalTime.now(ZoneId.of(zone));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String day = ld.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            txtCurrentDate.setText(new StringBuilder().append(day).append(", ").append(ld.format(dtf)).toString());
            dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            txtCurrentTime.setText(String.valueOf(lt.format(dtf)));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
    }
}

