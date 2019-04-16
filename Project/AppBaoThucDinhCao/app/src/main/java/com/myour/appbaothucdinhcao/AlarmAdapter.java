package com.myour.appbaothucdinhcao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Alarm> alarmList;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.main_fragment_recycleview_item,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarmSetting(v);
            }
        });
        return new ViewHolder(itemView);
    }

    private void openAlarmSetting(View v) {
        //startActivityForResult: AlarmMoreActivity
    }

    @Override
    //Truyền dữ liệu vào các control trong item layout
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarm alarm=alarmList.get(position);
        SwitchCompat swEnableAlarm=holder.swEnableAlarm;
        TextView tvHour=holder.tvHour;
        TextView  tvMinute=holder.tvMinute;
        ImageView imgAlarmType=holder.imgAlarmType;

        swEnableAlarm.setChecked(alarm.isEnableAlarm());
        if (alarm.getHour()<10){
            tvHour.setText("0"+String.valueOf(alarm.getHour()));
        }
        else {
            tvHour.setText(String.valueOf(alarm.getHour()));
        }
        if (alarm.getMinute()<10){
            tvMinute.setText("0"+String.valueOf(alarm.getMinute()));
        }
        else {
            tvMinute.setText(String.valueOf(alarm.getMinute()));
        }
        imgAlarmType.setImageResource(alarm.getImgAlarmType());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private SwitchCompat swEnableAlarm;
        private TextView tvHour;
        private TextView tvMinute;
        private ImageView imgAlarmType;

        public ViewHolder(View itemView) {
            super(itemView);
            swEnableAlarm=itemView.findViewById(R.id.swEnableAlarm);
            tvHour=itemView.findViewById(R.id.tvHour);
            tvMinute=itemView.findViewById(R.id.tvMinute);
            imgAlarmType=itemView.findViewById(R.id.imgAlarmType);
        }
    }
}
