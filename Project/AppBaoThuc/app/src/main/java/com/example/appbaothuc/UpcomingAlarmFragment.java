package com.example.appbaothuc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.appbaothuc.alarmsetting.SettingAlarmActivity;

import java.util.ArrayList;
import java.util.List;


public class UpcomingAlarmFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerViewListAlarm;
    private ImageButton buttonAddAlarm;
    private List<Alarm> listAlarm = new ArrayList<>();
    private AlarmAdapter alarmAdapter;

    public AlarmAdapter getAlarmAdapter(){
        return this.alarmAdapter;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false);
        databaseHandler = new DatabaseHandler(getContext());
        recyclerViewListAlarm = view.findViewById(R.id.recyclerView_list_alarm);
        buttonAddAlarm = view.findViewById(R.id.button_add_alarm);
        buttonAddAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext(), SettingAlarmActivity.class);
                intent.putExtra("mode", "add");
                intent.putExtra("alarmAdapter", alarmAdapter);
                startActivity(intent);
            }
        });
        alarmAdapter = new AlarmAdapter(getContext(), listAlarm);
        recyclerViewListAlarm.setAdapter(alarmAdapter);
        recyclerViewListAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}