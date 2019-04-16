package com.example.appbaothuc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.appbaothuc.alarmsetting.SettingAlarmFragment;
import com.example.appbaothuc.interfaces.OnOpenSettingAlarmFragment;
import com.example.appbaothuc.interfaces.SettingAlarmFragmentListener;

import java.util.Collections;
import java.util.List;


public class UpcomingAlarmFragment extends Fragment implements SettingAlarmFragmentListener {
    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerViewListAlarm;
    private ImageButton buttonAddAlarm;
    private List<Alarm> listAlarm;
    private AlarmAdapter alarmAdapter;
    private SettingAlarmFragment settingAlarmFragment;
    private FragmentManager fragmentManager;
    private OnOpenSettingAlarmFragment listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false);

        recyclerViewListAlarm = view.findViewById(R.id.recycler_view_list_alarm);
        buttonAddAlarm = view.findViewById(R.id.button_add_alarm);

        databaseHandler = new DatabaseHandler(getContext());
        listAlarm = databaseHandler.getAllAlarm();
        Collections.sort(listAlarm);
        MainActivity.restartAlarmService(getContext());
        alarmAdapter = new AlarmAdapter(getContext(), this, listAlarm);
        settingAlarmFragment = new SettingAlarmFragment();

        recyclerViewListAlarm.setAdapter(alarmAdapter);
        recyclerViewListAlarm.setItemAnimator(null);
        recyclerViewListAlarm.setLayoutManager(new LinearLayoutManager(getContext()));
        settingAlarmFragment.setEnterTransition(new Slide(Gravity.END));
        settingAlarmFragment.setExitTransition(new Slide(Gravity.END));
        fragmentManager = getFragmentManager();
        listener = settingAlarmFragment;

        buttonAddAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                fragmentManager.beginTransaction().replace(R.id.full_screen_fragment_container, settingAlarmFragment).commit();
                listener.onInitialize(UpcomingAlarmFragment.this, null);
            }
        });
        return view;
    }
















    // Override
    @Override
    public void onAddNewAlarm(Alarm alarm) {
        databaseHandler.insertAlarm(alarm);
        alarm.setIdAlarm(databaseHandler.getRecentAddedAlarm().getIdAlarm());
        listAlarm.add(alarm);
        Collections.sort(listAlarm);
        alarmAdapter.notifyDataSetChanged();
        MainActivity.restartAlarmService(getContext());
    }

    @Override
    public void onEditAlarm(Alarm alarm) {
        databaseHandler.updateAlarm(alarm);
        for(int i = 0; i < listAlarm.size(); i++){
            if(listAlarm.get(i).getIdAlarm() == alarm.getIdAlarm()){
                listAlarm.set(i, alarm);
                Collections.sort(listAlarm);
                alarmAdapter.notifyDataSetChanged();
                break;
            }
        }
        MainActivity.restartAlarmService(getContext());
    }
}