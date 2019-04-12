package com.example.appbaothuc.alarmsetting;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;

public class TypePlayCameraActivity extends AppCompatActivity {

    private LinearLayout linearLayoutTypePlayCamera;
    private ImageButton imageButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_play_camera);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        linearLayoutTypePlayCamera = findViewById(R.id.linearLayoutTypePlayCamera);
        linearLayoutTypePlayCamera.setBackground(wallpaperDrawable);

        imageButtonExit = findViewById(R.id.imageButtonCameraExit);
//        textViewHour = findViewById(R.id.textViewHourDefault);
//        textViewMinute = findViewById(R.id.textViewMinuteDefault);
//        TimePicker timePicker = new TimePicker(TypePlayDefaultActivity.this);
//        textViewHour.setText(timePicker.getHour()+"");
//        textViewMinute.setText(timePicker.getMinute()+"");
//
        imageButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
