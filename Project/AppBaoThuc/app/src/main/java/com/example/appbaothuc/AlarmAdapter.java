package com.example.appbaothuc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private Context context;
    private List<Alarm> listAlarm;
    public AlarmAdapter(Context context, List<Alarm> listAlarm) {
        this.context = context;
        this.listAlarm = listAlarm;
    }
    public void openAlarmSetting(View v){
        Intent intent = new Intent(context, SettingAlarmActivity.class);
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
        CheckBox checkBoxEnable = viewHolder.checkBoxEnable;
        TextView textViewHour = viewHolder.textViewHour;
        TextView textViewMinute = viewHolder.textViewMinute;

        checkBoxEnable.setChecked(alarm.isEnable());
        textViewHour.setText(String.valueOf(alarm.getHour()));
        if(alarm.getMinute() < 10){
            textViewMinute.setText("0" + String.valueOf(alarm.getMinute()));
        }
        else{
            textViewMinute.setText(String.valueOf(alarm.getMinute()));
        }
    }
    @Override
    public int getItemCount() {
        return this.listAlarm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBoxEnable;
        private TextView textViewHour;
        private TextView textViewMinute;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxEnable = itemView.findViewById(R.id.checkBox_enable);
            textViewHour = itemView.findViewById(R.id.textView_hour);
            textViewMinute = itemView.findViewById(R.id.textView_minute);


            // TODO: Debug purpose.
            textViewHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context.getApplicationContext(), AlarmService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent , 0);
                    Calendar time = Calendar.getInstance();
                    time.setTimeInMillis(System.currentTimeMillis());
                    time.add(Calendar.SECOND, 5);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
                    Toast.makeText(context, "Start Alarm", Toast.LENGTH_LONG).show();
                }
            });
            // TODO:...
        }
    }
}