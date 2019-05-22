package com.example.appbaothuc.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.appbaothuc.models.Alarm;

public class MusicPlayerService extends Service {
    private Alarm alarm;
    private AlarmMusicPlayer alarmMusicPlayer;
    private static boolean playing;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.alarmMusicPlayer = new AlarmMusicPlayer(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmMusicPlayerCommand command = (AlarmMusicPlayerCommand)intent.getSerializableExtra("command");
        switch(command){
            case START:
                if(!playing){
                    playing = true;
                    byte[] byteAlarm = intent.getByteArrayExtra("byteAlarm");
                    this.alarm = Alarm.toParcelable(byteAlarm, Alarm.CREATOR);
                    this.alarmMusicPlayer.setAlarm(this.alarm);
                    this.alarmMusicPlayer.start();
                }
                break;
            case STOP:
                stopSelf();
                break;
            case MUTE_A_LITTLE:
                this.alarmMusicPlayer.muteALittle();
                break;
            case RESUME:
                alarmMusicPlayer.resume();
                break;
            case CHANGE_HEADSET_STATE:
                this.alarmMusicPlayer.setHeadsetState(intent.getIntExtra("state", 2));
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static boolean isPlaying() {
        return playing;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playing = false;
        this.alarmMusicPlayer.stopPlaying();
        this.alarmMusicPlayer = null;
    }


    public enum AlarmMusicPlayerCommand{
        START, STOP, MUTE_A_LITTLE, RESUME, CHANGE_HEADSET_STATE
    }
}
