package com.example.appbaothuc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File("/sdcard/download/boss battle a.flac")));
//        mediaPlayer.setLooping(true);
//        mediaPlayer.start();
        Intent intent = new Intent(this, ChallengeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Start alarm", Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
