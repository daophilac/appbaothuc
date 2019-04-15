package com.example.appbaothuc.alarmsetting;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.appbaothuc.R;

public class TypePlayDefaultActivity extends AppCompatActivity {

    private LinearLayout linearLayoutTypePlayDefault;
    private ImageButton imageButtonExit;
    private TextView textViewHour, textViewMinute;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_play_default);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        linearLayoutTypePlayDefault = findViewById(R.id.linearLayoutTypePlayDefault);
        linearLayoutTypePlayDefault.setBackground(wallpaperDrawable);

        imageButtonExit = findViewById(R.id.imageButtonDefaultExit);
        textViewHour = findViewById(R.id.textViewHourDefault);
        textViewMinute = findViewById(R.id.textViewMinuteDefault);
        TimePicker timePicker = new TimePicker(TypePlayDefaultActivity.this);
        textViewHour.setText(timePicker.getHour()+"");
        textViewMinute.setText(timePicker.getMinute()+"");

        imageButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
