package com.example.appbaothuc.services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

public class ServiceRestarter extends JobIntentService {
    private static final int JOB_ID = 0x01;
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ServiceRestarter.class, JOB_ID, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Intent notificationIntent = new Intent(getBaseContext(), NotificationService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getBaseContext().startForegroundService(notificationIntent);
        }
        else{
            getBaseContext().startService(notificationIntent);
        }
    }
}