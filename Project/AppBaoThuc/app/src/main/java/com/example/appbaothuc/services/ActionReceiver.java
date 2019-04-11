package com.example.appbaothuc.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ServiceCreator.enqueueWork(context, new Intent());
        }
        if (Intent.ACTION_TIME_CHANGED.equals(intent.getAction())){
            ServiceCreator.enqueueWork(context, new Intent());
        }
        if(Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())){
            ServiceCreator.enqueueWork(context, new Intent());
        }
    }
}