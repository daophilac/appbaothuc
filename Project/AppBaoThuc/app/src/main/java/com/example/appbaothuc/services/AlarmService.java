package com.example.appbaothuc.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.challenge.ChallengeActivity;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getBaseContext(), "Start alarm", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Alarm alarm = intent.getExtras().getParcelable("alarm");
        Intent alarmIntent = new Intent(this, ChallengeActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("alarm", alarm);
        startActivity(alarmIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}