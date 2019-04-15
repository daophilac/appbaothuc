package com.example.appbaothuc.alarmsetting;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;

public class TypePlayMathActivity extends AppCompatActivity {
    private LinearLayout linearLayoutTypePlayMath;
    private ImageButton imageButtonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_play_math);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        linearLayoutTypePlayMath = findViewById(R.id.linearLayoutTypePlayMath);
        linearLayoutTypePlayMath.setBackground(wallpaperDrawable);

        imageButtonExit = findViewById(R.id.imageButtonMathExit);
        imageButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
