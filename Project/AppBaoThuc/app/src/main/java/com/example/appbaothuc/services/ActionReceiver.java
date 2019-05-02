package com.example.appbaothuc.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.CHANGE_HEADSET_STATE;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())){
            intent.setClass(context, MusicPlayerService.class);
            intent.putExtra("command", CHANGE_HEADSET_STATE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                context.startForegroundService(intent);
                context.startService(intent);
            }
            else{
                context.startService(intent);
            }
        }
    }
}