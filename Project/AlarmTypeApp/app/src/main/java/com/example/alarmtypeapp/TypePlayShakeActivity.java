package com.example.alarmtypeapp;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class TypePlayShakeActivity extends AppCompatActivity {

    private LinearLayout linearLayoutTypeShakeDefault;
    private ImageButton imageButtonShakeExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_play_shake);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        linearLayoutTypeShakeDefault = findViewById(R.id.linearLayoutTypePlayShake);
        linearLayoutTypeShakeDefault.setBackground(wallpaperDrawable);

        imageButtonShakeExit = findViewById(R.id.imageButtonShakeExit);
        imageButtonShakeExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
