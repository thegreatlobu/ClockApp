package com.example.clockapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeZonesRecViewAdapter extends RecyclerView.Adapter<TimeZonesRecViewAdapter.ViewHolder> {
    private ArrayList<TimeZone> zones = new ArrayList<>();
    private Context context;
    public static final String TIME_ZONE_KEY = "time_zone_key";

    public TimeZonesRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_zone_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.timeZoneName.setText(zones.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public void setZones(ArrayList<TimeZone> zones) {
        this.zones = zones;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView timeZoneName;
        private ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeZoneName = itemView.findViewById(R.id.txtTimeZoneName);
            parent = itemView.findViewById(R.id.parent);

            timeZoneName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimeZone timeZone = zones.get(getAdapterPosition());
                    Utils.getInstance().addToDisplayZones(timeZone);
                    notifyDataSetChanged();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}
