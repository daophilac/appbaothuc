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
import java.util.Arrays;
import java.util.List;


public class UpcomingAlarmFragment extends Fragment {
    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerViewListAlarm;
    private ImageButton buttonAddAlarm;
    private List<Alarm> listAlarm = new ArrayList<>();
    private AlarmAdapter alarmAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false);
        databaseHandler = new DatabaseHandler(getContext());
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
        List<Integer> listRepeatDay = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
        Alarm alarm = new Alarm(true, 5,0, listRepeatDay);
        databaseHandler.insertAlarm(alarm);
        alarm.setIdAlarm(Integer.parseInt(databaseHandler.getLastAlarm().get("IdAlarm")));
        listAlarm.add(alarm);
        alarmAdapter.notifyItemInserted(listAlarm.size() - 1);
    }
}