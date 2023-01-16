package com.example.clockapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DisplayTimeZoneRecViewAdapter extends RecyclerView.Adapter<DisplayTimeZoneRecViewAdapter.ViewHolder> {
    private static final String TAG = "DisplayTimeZoneRecViewA";
    private ArrayList<TimeZone> zones = new ArrayList<>();
    private Context context;
    Handler handler = new Handler(Looper.getMainLooper());

    public DisplayTimeZoneRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_time_zone, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtDisplayName.setText(zones.get(position).getName());
        TimeThread thread = new TimeThread(holder);
        new Thread(thread).start();

    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public void setZones(ArrayList<TimeZone> zones) {
        this.zones = zones;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtDisplayName, txtDisplayDate, txtDisplayTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDisplayDate = itemView.findViewById(R.id.txtDisplayDate);
            txtDisplayName = itemView.findViewById(R.id.txtDisplayName);
            txtDisplayTime = itemView.findViewById(R.id.txtDisplayTime);
        }
    }

    public class TimeThread implements Runnable{
        ViewHolder holder;

        public TimeThread(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            handler.postDelayed(this, 1000);
            TimeZone zone = zones.get(holder.getAdapterPosition());
            ZoneId zid = ZoneId.of(zone.getName());
            LocalTime lt = LocalTime.now(zid);
            LocalDate ld = LocalDate.now(zid);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            holder.txtDisplayTime.setText(lt.format(dtf));
            dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String day = ld.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            holder.txtDisplayDate.setText(new StringBuilder().append(day).append(", ").append(ld.format(dtf)).toString());
        }
    }
}
