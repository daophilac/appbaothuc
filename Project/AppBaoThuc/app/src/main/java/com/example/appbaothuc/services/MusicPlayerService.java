package com.example.appbaothuc.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.appbaothuc.models.Alarm;

import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.START;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.STOP;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.MUTE_A_LITTLE;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.CHANGE_HEADSET_STATE;

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
        int command = intent.getIntExtra("command", 0);
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


    public static final class AlarmMusicPlayerCommand{
        public static final int START = 1;
        public static final int STOP = 2;
        public static final int MUTE_A_LITTLE = 3;
        public static final int CHANGE_HEADSET_STATE = 4;
    }
}
