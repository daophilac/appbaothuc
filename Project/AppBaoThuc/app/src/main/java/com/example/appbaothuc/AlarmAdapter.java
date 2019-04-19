package com.example.appbaothuc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.appbaothuc.alarmsetting.SettingAlarmFragment;

import java.util.Collections;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private UpcomingAlarmFragment upcomingAlarmFragment;
    private List<Alarm> listAlarm;
    private SparseArray<AlarmViewHolder> mapAlarmView;

    public AlarmAdapter(Context context, UpcomingAlarmFragment upcomingAlarmFragment, List<Alarm> listAlarm) {
        this.context = context;
        this.upcomingAlarmFragment = upcomingAlarmFragment;
        this.listAlarm = listAlarm;

        this.mapAlarmView = new SparseArray<>();
    }

    @Override
    public int getItemCount() {
        return this.listAlarm.size();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View alarmView = layoutInflater.inflate(R.layout.item_alarm, viewGroup, false);

        alarmView.setId(View.generateViewId());
        alarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmSetting(v);
            }
        });
        AlarmViewHolder alarmViewHolder = new AlarmViewHolder(alarmView);
        mapAlarmView.put(alarmView.getId(), alarmViewHolder);
        return alarmViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmViewHolder alarmViewHolder, int i) {
        final Alarm alarm = listAlarm.get(i);
        alarmViewHolder.alarm = alarm;

        final CheckBox checkBoxEnable = alarmViewHolder.checkBoxEnable;
        TextView textViewHour = alarmViewHolder.textViewHour;
        TextView textViewMinute = alarmViewHolder.textViewMinute;
        TextView textViewDescribeRepeatDay = alarmViewHolder.textViewDescribeRepeatDay;

        checkBoxEnable.setId(View.generateViewId());

        checkBoxEnable.setChecked(alarm.isEnable());
        textViewHour.setText(String.valueOf(alarm.getHour()));
        if (alarm.getMinute() < 10) {
            textViewMinute.setText("0" + String.valueOf(alarm.getMinute()));
        } else {
            textViewMinute.setText(String.valueOf(alarm.getMinute()));
        }
        textViewDescribeRepeatDay.setText(alarm.getDescribeRepeatDay());

        checkBoxEnable.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alarm.setEnable(checkBoxEnable.isChecked());
                upcomingAlarmFragment.updateAlarmEnable(alarm);
                listAlarm.set(alarmViewHolder.getAdapterPosition(), alarm);
                Collections.sort(listAlarm);
                AlarmAdapter.this.notifyDataSetChanged();
            }
        });
    }
    public void openAlarmSetting(View v) {
        SettingAlarmFragment settingAlarmFragment = SettingAlarmFragment.newInstance(upcomingAlarmFragment, mapAlarmView.get(v.getId()).alarm);
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.full_screen_fragment_container, settingAlarmFragment).commit();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private Alarm alarm;
        private CheckBox checkBoxEnable;
        private TextView textViewHour;
        private TextView textViewMinute;
        private TextView textViewDescribeRepeatDay;

        AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxEnable = itemView.findViewById(R.id.check_box_enable);
            textViewHour = itemView.findViewById(R.id.text_view_hour);
            textViewMinute = itemView.findViewById(R.id.text_view_minute);
            textViewDescribeRepeatDay = itemView.findViewById(R.id.text_view_describe_repeat_day);
        }
    }
}