package com.example.appbaothuc;

import android.content.Context;
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
import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.ShakeDetail;
import com.example.appbaothuc.services.NotificationService;

import java.util.Collections;
import java.util.List;

import static com.example.appbaothuc.models.ChallengeType.DEFAULT;
import static com.example.appbaothuc.models.ChallengeType.MATH;
import static com.example.appbaothuc.models.ChallengeType.SHAKE;


public class UpcomingAlarmFragment extends Fragment {
    private MainActivity mainActivity;
    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerViewListAlarm;
    private ImageButton buttonAddAlarm;
    private List<Alarm> listAlarm;
    private AlarmAdapter alarmAdapter;
    private SettingAlarmFragment settingAlarmFragment;
    private FragmentManager fragmentManager;
    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
        databaseHandler = new DatabaseHandler(getContext());
        settingAlarmFragment = new SettingAlarmFragment();
        settingAlarmFragment.setEnterTransition(new Slide(Gravity.END));
        settingAlarmFragment.setExitTransition(new Slide(Gravity.END));
        listAlarm = databaseHandler.getAllAlarm();
        Collections.sort(listAlarm);
        alarmAdapter = new AlarmAdapter(getContext(), this, listAlarm);
        fragmentManager = getFragmentManager();
        mainActivity.appSettingFragment.setOnHourModeChangeListener(new AppSettingFragment.OnHourModeChangeListener() {
            @Override
            public void onHourModeChange(int hourMode) {
                alarmAdapter.notifyDataSetChanged();
                NotificationService.changeHourMode(context);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_alarm, container, false);
        recyclerViewListAlarm = view.findViewById(R.id.recycler_view_list_alarm);
        buttonAddAlarm = view.findViewById(R.id.button_add_alarm);
        recyclerViewListAlarm.setAdapter(alarmAdapter);
        recyclerViewListAlarm.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonAddAlarm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                settingAlarmFragment.configure(UpcomingAlarmFragment.this, null);
                fragmentManager.beginTransaction()
                        .add(R.id.full_screen_fragment_container, settingAlarmFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    public void addAlarm(Alarm alarm){
        alarm.setChallengeType(DEFAULT);
        this.databaseHandler.insertAlarm(alarm);
        alarm.setIdAlarm(databaseHandler.getRecentAddedAlarm().getIdAlarm());
        listAlarm.add(alarm);
        Collections.sort(listAlarm);
        alarmAdapter.notifyDataSetChanged();
        NotificationService.update(getContext());
    }
    public void addAlarm(Alarm alarm, MathDetail mathDetail){
        alarm.setChallengeType(MATH);
        this.databaseHandler.insertAlarm(alarm);
        alarm.setIdAlarm(databaseHandler.getRecentAddedAlarm().getIdAlarm());
        mathDetail.setIdAlarm(alarm.getIdAlarm());
        this.databaseHandler.insertMathDetail(mathDetail);
        listAlarm.add(alarm);
        Collections.sort(listAlarm);
        alarmAdapter.notifyDataSetChanged();
        NotificationService.update(getContext());
    }
    public void addAlarm(Alarm alarm, ShakeDetail shakeDetail){
        alarm.setChallengeType(SHAKE);
        this.databaseHandler.insertAlarm(alarm);
        alarm.setIdAlarm(databaseHandler.getRecentAddedAlarm().getIdAlarm());
        shakeDetail.setIdAlarm(alarm.getIdAlarm());
        this.databaseHandler.insertShakeDetail(shakeDetail);
        listAlarm.add(alarm);
        Collections.sort(listAlarm);
        alarmAdapter.notifyDataSetChanged();
        NotificationService.update(getContext());
    }
    public void editAlarm(Alarm alarm){
        if(alarm.getChallengeType() != DEFAULT){
            this.databaseHandler.deleteChallengeDetail(alarm.getIdAlarm(), alarm.getChallengeType());
        }
        alarm.setChallengeType(DEFAULT);
        this.databaseHandler.updateAlarm(alarm);
        for(int i = 0; i < listAlarm.size(); i++){
            if(listAlarm.get(i).getIdAlarm() == alarm.getIdAlarm()){
                listAlarm.set(i, alarm);
                Collections.sort(listAlarm);
                alarmAdapter.notifyDataSetChanged();
                break;
            }
        }
        NotificationService.update(getContext());
    }
    public void editAlarm(Alarm alarm, MathDetail mathDetail){
        if(alarm.getChallengeType() != MATH){
            if(alarm.getChallengeType() != DEFAULT){
                this.databaseHandler.deleteChallengeDetail(alarm.getIdAlarm(), alarm.getChallengeType());
            }
            this.databaseHandler.insertMathDetail(mathDetail);
        }
        else{
            if(mathDetail != null){
                this.databaseHandler.updateMathDetail(mathDetail);
            }
        }
        alarm.setChallengeType(MATH);
        this.databaseHandler.updateAlarm(alarm);
        for(int i = 0; i < listAlarm.size(); i++){
            if(listAlarm.get(i).getIdAlarm() == alarm.getIdAlarm()){
                listAlarm.set(i, alarm);
                Collections.sort(listAlarm);
                alarmAdapter.notifyDataSetChanged();
                break;
            }
        }
        NotificationService.update(getContext());
    }
    public void editAlarm(Alarm alarm, ShakeDetail shakeDetail){
        if(alarm.getChallengeType() != SHAKE){
            if(alarm.getChallengeType() != DEFAULT){
                this.databaseHandler.deleteChallengeDetail(alarm.getIdAlarm(), alarm.getChallengeType());
            }
            this.databaseHandler.insertShakeDetail(shakeDetail);
        }
        else{
            if(shakeDetail != null){
                this.databaseHandler.updateShakeDetail(shakeDetail);
            }
        }
        alarm.setChallengeType(SHAKE);
        this.databaseHandler.updateAlarm(alarm);
        for(int i = 0; i < listAlarm.size(); i++){
            if(listAlarm.get(i).getIdAlarm() == alarm.getIdAlarm()){
                listAlarm.set(i, alarm);
                Collections.sort(listAlarm);
                alarmAdapter.notifyDataSetChanged();
                break;
            }
        }
        NotificationService.update(getContext());
    }
    public void deleteAlarm(int idAlarm){
        databaseHandler.deleteAlarm(idAlarm);
        for(int i = 0; i < listAlarm.size(); i++){
            if(listAlarm.get(i).getIdAlarm() == idAlarm){
                listAlarm.remove(i);
                alarmAdapter.notifyItemRemoved(i);
                NotificationService.update(getContext());
                break;
            }
        }
    }
    public void updateAlarmEnable(Alarm alarm){
        databaseHandler.updateAlarmEnable(alarm.getIdAlarm(), alarm.isEnable());
        NotificationService.update(getContext());
    }
}