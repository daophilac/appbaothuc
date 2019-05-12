package com.example.appbaothuc.services;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.appbaothuc.models.Alarm;

import static com.example.appbaothuc.appsetting.AppSettingFragment.canMuteAlarmFor;
import static com.example.appbaothuc.appsetting.AppSettingFragment.graduallyIncreaseVolume;
import static com.example.appbaothuc.appsetting.AppSettingFragment.muteAlarmIn;

public class AlarmMusicPlayer {
    private Context context;
    private Alarm alarm;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    private Uri uri;
    private float alarmVolume;
    private long[] timings;
    private int[] amplitudes;

    private AudioManager audioManager;
    private int currentAudioMode;
    private int currentAudioVolume;
    private int maxAudioVolume;
    private int alarmMaxVolumeLevel;
    private float alarmVolumeInPercent;
    private int headsetState;

    private boolean isRunning;
    private boolean isDismissed;
    private boolean isMuting;
    private boolean snoozeAgain;
    private Thread threadSnooze;
    private int muteTime;

    public AlarmMusicPlayer(Context context){
        this.context = context;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void setHeadsetState(int headsetState){
        this.headsetState = headsetState;
        if(this.isRunning){
            if(headsetState == 1){
                this.audioManager.setMode(AudioManager.STREAM_MUSIC);
                this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.maxAudioVolume, 0);
            }
            else{
                this.audioManager.setMode(AudioManager.MODE_NORMAL);
                this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (this.alarmVolumeInPercent * this.maxAudioVolume) , 0);
            }
        }
    }
    private void initialize(){
        this.muteTime = 0;
        this.uri = Uri.parse(this.alarm.getRingtone().getUrl());
        this.alarmVolume = this.alarm.getVolume();
        if(this.alarmVolume == 0){
            this.alarmVolume = 1000;
        }
        this.alarmMaxVolumeLevel = 1000;
        this.audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        this.currentAudioMode = this.audioManager.getMode();
        this.maxAudioVolume = this.audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        this.currentAudioVolume = this.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        this.alarmVolumeInPercent = this.alarmVolume / this.alarmMaxVolumeLevel;
        if(this.alarm.isVibrate()){
            this.timings = new long[]{500, 500, 1000, 500, 500, 3000};
            this.amplitudes = new int[]{0, 127, 255, 127, 0, 0};
            this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        }


        this.mediaPlayer = MediaPlayer.create(this.context, this.uri);
        if(headsetState == 1){
            this.audioManager.setMode(AudioManager.STREAM_MUSIC);
            this.audioManager.setSpeakerphoneOn(true);
            this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.maxAudioVolume, 0);
        }
        else{
            this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (this.alarmVolumeInPercent * this.maxAudioVolume) , 0);
        }
    }
    public void start() {
        initialize();
        this.isRunning = true;
        if(this.alarm.isVibrate()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                this.vibrator.vibrate(VibrationEffect.createWaveform(this.timings, this.amplitudes, 0));
            }
            else{
                this.vibrator.vibrate(timings, 0);
            }
        }
        if (!graduallyIncreaseVolume){
            this.mediaPlayer.setVolume(this.alarmVolumeInPercent, this.alarmVolumeInPercent);
            this.mediaPlayer.start();
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.setVolume(0,0);
                    mediaPlayer.start();
                    for (float i = 1; i <= 1000; i++) {
                        try {
                            if (isDismissed) {
                                return;
                            }
                            if (isMuting) {
                                return;
                            }
                            mediaPlayer.setVolume(i / 1000, i / 1000);
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
    public void muteALittle(){
        if(canMuteAlarmFor == 0){
            Toast.makeText(context, "Based on your setting, you can't mute the alarm.", Toast.LENGTH_LONG).show();
            return;
        }
        if(muteTime >= canMuteAlarmFor){
            Toast.makeText(context, "You can't mute the alarm for more than " + canMuteAlarmFor + " times", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "Mute for " + muteAlarmIn + " seconds.", Toast.LENGTH_LONG).show();
        SnoozeManager snoozeManager = new SnoozeManager(muteAlarmIn);
        if (this.threadSnooze == null || !this.threadSnooze.isAlive()) {
            this.threadSnooze = new Thread(snoozeManager);
            this.threadSnooze.start();
        }
        else {
            this.snoozeAgain = true;
        }
    }
    public void stopPlaying(){
        if(!this.isRunning){
            return;
        }
        this.isRunning = false;
        this.isDismissed = true;
        if(this.alarm.isVibrate()){
            this.vibrator.cancel();
        }
        this.mediaPlayer.release();
        this.audioManager.setMode(this.currentAudioMode);
        if(headsetState == 1){
            this.audioManager.setSpeakerphoneOn(false);
        }
        this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.currentAudioVolume, 0);
    }




    private class SnoozeManager implements Runnable {
        private int snoozeTime;
        SnoozeManager(int snoozeTime) {
            this.snoozeTime = snoozeTime;
        }
        public void run() {
            isMuting = true;
            if(alarm.isVibrate()){
                vibrator.cancel();
            }
            mediaPlayer.setVolume(0, 0);
            try {
                Thread.sleep(this.snoozeTime * 1000);
                isMuting = false;
                if(alarm.isVibrate()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, 0));
                    }
                    else{
                        vibrator.vibrate(timings, 0);
                    }
                }
                if (!graduallyIncreaseVolume){
                    mediaPlayer.setVolume(alarmVolumeInPercent, alarmVolumeInPercent);
                    muteTime++;
                    return;
                }
                for (float i = 1; i <= 1000; i++) {
                    if (isDismissed) {
                        if(alarm.isVibrate()){
                            vibrator.cancel();
                        }
                        return;
                    }
                    if (snoozeAgain) {
                        snoozeAgain = false;
                        run();
                        return;
                    }
                    mediaPlayer.setVolume(i / 1000, i / 1000);
                    Thread.sleep(10);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            muteTime++;
        }
    }
}