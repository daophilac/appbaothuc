package com.example.alarmtypeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class TypeActivity extends AppCompatActivity {
    private Button btnCancel, btnOK;
    private ImageButton imageButtonDefault, imageButtonCamera, imageButtonShake, imageButtonMath,
                imageButtonQRCode;
    private LinearLayout linearLayoutDefault, linearLayoutCamera, linearLayoutShake, linearLayoutMath,
                linearLayoutQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        setControll();
    }
    public void setControll(){
        btnCancel = findViewById(R.id.btnCancel);
        btnOK = findViewById(R.id.btnOK);
        imageButtonDefault = findViewById(R.id.imageButtonDefault);
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonShake = findViewById(R.id.imageButtonShake);
        imageButtonMath = findViewById(R.id.imageButtonMath);
        imageButtonQRCode = findViewById(R.id.imageButtonQRCode);
        linearLayoutDefault = findViewById(R.id.linearLayoutDefault);
        linearLayoutCamera = findViewById(R.id.linearLayoutCamera);
        linearLayoutShake = findViewById(R.id.linearLayoutShake);
        linearLayoutMath = findViewById(R.id.linearLayoutMath);
        linearLayoutQRCode = findViewById(R.id.linearLayoutQRCode);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, SettingAlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, SettingAlarmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, TypePlayDefaultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, TypePlayCameraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, TypePlayShakeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeActivity.this, TypePlayMathActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonQRCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("hihi", "haha");
            }
        });
    }
}
