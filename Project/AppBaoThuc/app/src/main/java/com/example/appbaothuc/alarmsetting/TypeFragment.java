package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;

public class TypeFragment extends Fragment {
    private Button btnCancel, btnOK;
    private ImageButton imageButtonDefault, imageButtonCamera, imageButtonShake, imageButtonMath,
            imageButtonQRCode;
    private LinearLayout linearLayoutDefault, linearLayoutCamera, linearLayoutShake, linearLayoutMath,
            linearLayoutQRCode;
    private Context context;
    private Integer challengeType;

    private FragmentManager fragmentManager;
    private TypeLinearMathFragment typeLinearMathFragment;
//
//    public interface TypeFragmentListener{
//        void onFinishTypeFragment(Integer inputNumber);
//    }
//    TypeFragmentListener listener;
//    public void setListener(SettingAlarmFragment settingAlarmFragment){
//        this.listener = settingAlarmFragment;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_type, container, false);
        setControl(view);
        return view;
    }

    public void setControl(View view){
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOK = view.findViewById(R.id.btnOK);
        imageButtonDefault = view.findViewById(R.id.imageButtonDefault);
        imageButtonCamera = view.findViewById(R.id.imageButtonCamera);
        imageButtonShake = view.findViewById(R.id.imageButtonShake);
        imageButtonMath = view.findViewById(R.id.imageButtonMath);
        imageButtonQRCode = view.findViewById(R.id.imageButtonQRCode);
        linearLayoutDefault = view.findViewById(R.id.linearLayoutDefault);
        linearLayoutCamera = view.findViewById(R.id.linearLayoutCamera);
        linearLayoutShake = view.findViewById(R.id.linearLayoutShake);
        linearLayoutMath = view.findViewById(R.id.linearLayoutMath);
        linearLayoutQRCode = view.findViewById(R.id.linearLayoutQRCode);

        challengeType = SettingAlarmFragment.challengeType;

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingAlarmFragment.challengeType = challengeType;
                getFragmentManager().beginTransaction().remove(TypeFragment.this).commit();
                //TODO:
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(TypeFragment.this).commit();
            }
        });
        imageButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TypePlayDefaultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TypePlayCameraActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TypePlayShakeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TypePlayMathActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        imageButtonQRCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        linearLayoutDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeType = 0;
                linearLayoutDefault.setBackgroundColor(Color.BLUE);
                linearLayoutMath.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutCamera.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutQRCode.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutShake.setBackgroundColor(getResources().getColor(R.color.colortext2));
            }
        });
        linearLayoutMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutMath.setBackgroundColor(Color.BLUE);
                linearLayoutDefault.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutCamera.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutShake.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutQRCode.setBackgroundColor(getResources().getColor(R.color.colortext2));
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container, typeLinearMathFragment).commit();
                //challengeType = 1;
            }
        });
        linearLayoutShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeType = 2;
                linearLayoutShake.setBackgroundColor(Color.BLUE);
                linearLayoutDefault.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutCamera.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutMath.setBackgroundColor(getResources().getColor(R.color.colortext2));
                linearLayoutQRCode.setBackgroundColor(getResources().getColor(R.color.colortext2));
            }
        });
    }
}
