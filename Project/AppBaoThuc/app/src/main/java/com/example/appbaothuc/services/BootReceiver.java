package com.example.appbaothuc.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.v("DAOPHILAC", "of course");
//        Intent service = new Intent(context, AlarmService.class);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            context.startForegroundService(service);
//        }
//        else{
//            context.startService(service);
//        }

        //ExternalFileWriter externalFileWriter = new ExternalFileWriter("/sdcard","test.txt", (Activity)context);
        //externalFileWriter.write("it can", false);
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ServiceRestarter.enqueueWork(context, new Intent());
        }
    }
}