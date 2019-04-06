package com.example.appbaothuc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
//        File file = new File("/sdcard/download/boss battle a.flac");
//        Uri uri = Uri.fromFile("")
        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File("/sdcard/download/boss battle a.flac")));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Toast.makeText(getBaseContext(), "Start music", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
