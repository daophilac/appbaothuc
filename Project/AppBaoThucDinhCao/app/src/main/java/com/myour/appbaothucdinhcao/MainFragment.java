package com.myour.appbaothucdinhcao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private RecyclerView recyclerViewListAlarm;
    private ImageButton btnAddAlarm;
    private ArrayList<Alarm> alarmList = new ArrayList<>();
    private AlarmAdapter alarmAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_recycleview, container, false);

        setControl(view);

        recyclerViewListAlarm.setHasFixedSize(true);
        alarmAdapter = new AlarmAdapter(getContext(), alarmList);
        recyclerViewListAlarm.setAdapter(alarmAdapter);
        recyclerViewListAlarm.setLayoutManager(new LinearLayoutManager(getContext()));

        setEvent();

        return view;
    }

    private void setControl(View view) {
        recyclerViewListAlarm = view.findViewById(R.id.recycleViewListAlarm);
        btnAddAlarm = view.findViewById(R.id.btnAddAlarm);
    }

    private void setEvent() {
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm alarm = new Alarm(true, 1, 1, R.drawable.ic_math);
                alarmList.add(alarm);
                alarmAdapter.notifyItemInserted(alarmList.size() - 1);
            }
        });
    }

}
