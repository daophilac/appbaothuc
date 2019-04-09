package com.example.appbaothuc;

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

import java.util.ArrayList;
import java.util.List;

public class UpcomingAlarmFragment extends Fragment {
    private RecyclerView recyclerViewListAlarm;
    private ImageButton buttonAddAlarm;
    private List<Alarm> listAlarm = new ArrayList<>();
    private AlarmAdapter alarmAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false);
        recyclerViewListAlarm = view.findViewById(R.id.recyclerView_list_alarm);
        buttonAddAlarm = view.findViewById(R.id.button_add_alarm);
        buttonAddAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addAlarm(v);
            }
        });
        alarmAdapter = new AlarmAdapter(getContext(), listAlarm);
        recyclerViewListAlarm.setAdapter(alarmAdapter);
        recyclerViewListAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    public void addAlarm(View view){
        // Khi người dùng bấm vào nút thêm alarm
        Alarm alarm = new Alarm(5,0,0,true);
        listAlarm.add(alarm);
        alarmAdapter.notifyItemInserted(listAlarm.size() - 1);
    }
}