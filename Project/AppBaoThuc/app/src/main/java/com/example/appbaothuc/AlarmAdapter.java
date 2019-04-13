package com.example.appbaothuc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.appbaothuc.services.NotificationService;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private Context context;
    private List<Alarm> listAlarm;

    public AlarmAdapter(Context context, List<Alarm> listAlarm) {
        this.context = context;
        this.listAlarm = listAlarm;
    }

    public void openAlarmSetting(View v) {
        Intent intent = new Intent(context, SettingAlarmActivity.class);
        intent.putExtra("alarm", listAlarm.get(0));
        //int a = ((RecyclerView.ViewHolder)v).idAlarm;
        //intent.putExtra("idAlarm", (ViewHolder)v).getItemId());
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View alarmView = layoutInflater.inflate(R.layout.alarm_item, viewGroup, false);
        alarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmSetting(v);
            }
        });
        return new ViewHolder(alarmView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Alarm alarm = listAlarm.get(i);
        viewHolder.idAlarm = alarm.getIdAlarm();
        CheckBox checkBoxEnable = viewHolder.checkBoxEnable;
        TextView textViewHour = viewHolder.textViewHour;
        TextView textViewMinute = viewHolder.textViewMinute;

        checkBoxEnable.setChecked(alarm.isEnable());
        textViewHour.setText(String.valueOf(alarm.getHour()));
        if (alarm.getMinute() < 10) {
            textViewMinute.setText("0" + String.valueOf(alarm.getMinute()));
        } else {
            textViewMinute.setText(String.valueOf(alarm.getMinute()));
        }
    }

    @Override
    public int getItemCount() {
        return this.listAlarm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private int idAlarm;
        private CheckBox checkBoxEnable;
        private TextView textViewHour;
        private TextView textViewMinute;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxEnable = itemView.findViewById(R.id.checkBox_enable);
            textViewHour = itemView.findViewById(R.id.textView_hour);
            textViewMinute = itemView.findViewById(R.id.textView_minute);

            final DatabaseHandler databaseHandler = new DatabaseHandler(context);
            // TODO: Debug purpose.
            textViewHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar time1 = Calendar.getInstance();
                    Calendar time2 = Calendar.getInstance();
                    Calendar time3 = Calendar.getInstance();
                    Calendar time4 = Calendar.getInstance();
                    Calendar time5 = Calendar.getInstance();
                    Calendar time6 = Calendar.getInstance();
                    Calendar time7 = Calendar.getInstance();
                    Calendar time8 = Calendar.getInstance();
                    Calendar time9 = Calendar.getInstance();
                    Calendar time0 = Calendar.getInstance();
                    time1.add(Calendar.MINUTE, 5);
                    time2.add(Calendar.MINUTE, 3);
                    time3.add(Calendar.MINUTE, 1);
                    time4.add(Calendar.MINUTE, 4);
                    time5.add(Calendar.MINUTE, 2);
                    time6.add(Calendar.MINUTE, 6);
                    time7.add(Calendar.MINUTE, 7);
                    time8.add(Calendar.MINUTE, 8);
                    time9.add(Calendar.MINUTE, 9);
                    time0.add(Calendar.MINUTE, 10);
                    int hour1 = time1.get(Calendar.HOUR_OF_DAY);
                    int hour2 = time2.get(Calendar.HOUR_OF_DAY);
                    int hour3 = time3.get(Calendar.HOUR_OF_DAY);
                    int hour4 = time4.get(Calendar.HOUR_OF_DAY);
                    int hour5 = time5.get(Calendar.HOUR_OF_DAY);
                    int hour6 = time6.get(Calendar.HOUR_OF_DAY);
                    int hour7 = time7.get(Calendar.HOUR_OF_DAY);
                    int hour8 = time8.get(Calendar.HOUR_OF_DAY);
                    int hour9 = time9.get(Calendar.HOUR_OF_DAY);
                    int hour0 = time0.get(Calendar.HOUR_OF_DAY);
                    int minute1 = time1.get(Calendar.MINUTE);
                    int minute2 = time2.get(Calendar.MINUTE);
                    int minute3 = time3.get(Calendar.MINUTE);
                    int minute4 = time4.get(Calendar.MINUTE);
                    int minute5 = time5.get(Calendar.MINUTE);
                    int minute6 = time6.get(Calendar.MINUTE);
                    int minute7 = time7.get(Calendar.MINUTE);
                    int minute8 = time8.get(Calendar.MINUTE);
                    int minute9 = time9.get(Calendar.MINUTE);
                    int minute0 = time0.get(Calendar.MINUTE);
                    List<Integer> listRepeatDay1 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay2 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay3 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay4 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay5 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay6 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay7 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay8 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay9 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    List<Integer> listRepeatDay0 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
                    databaseHandler.insertAlarm(true, hour1, minute1, listRepeatDay1);
                    databaseHandler.insertAlarm(true, hour2, minute2, listRepeatDay2);
                    databaseHandler.insertAlarm(true, hour3, minute3, listRepeatDay3);
                    databaseHandler.insertAlarm(true, hour4, minute4, listRepeatDay4);
                    databaseHandler.insertAlarm(true, hour5, minute5, listRepeatDay5);
                    databaseHandler.insertAlarm(true, hour6, minute6, listRepeatDay6);
                    databaseHandler.insertAlarm(true, hour7, minute7, listRepeatDay7);
                    databaseHandler.insertAlarm(true, hour8, minute8, listRepeatDay8);
                    databaseHandler.insertAlarm(true, hour9, minute9, listRepeatDay9);
                    databaseHandler.insertAlarm(true, hour0, minute0, listRepeatDay0);
                    Intent intent = new Intent(context, NotificationService.class);
                    context.startService(intent);
                }
            });
            // TODO:...
        }
    }
}