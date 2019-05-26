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
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;
import com.example.appbaothuc.challenge.ChallengeActivity;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.ChallengeType;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.ShakeDetail;


public class TypeFragment extends Fragment implements MathConfigurationFragment.MathConfigurationFragmentListener, ShakeConfigurationFragment.ShakeConfigurationFragmentListener, Animation.AnimationListener {
    private TypeFragmentListener listener;
    private ChallengeType currentChallengeType;
    private Alarm alarm;
    private LinearLayout linearLayoutDefault;
    private LinearLayout linearLayoutCamera;
    private LinearLayout linearLayoutShake;
    private LinearLayout linearLayoutMath;
    private LinearLayout linearLayoutQRCode;
    private ImageButton imageButtonDefault;
    private ImageButton imageButtonCamera;
    private ImageButton imageButtonShake;
    private ImageButton imageButtonMath;
    private ImageButton imageButtonQRCode;
    private Button btnOK;

    private LinearLayout layoutType;
    private Animation animFadein;

    private FragmentManager fragmentManager;
    private MathConfigurationFragment mathConfigurationFragment;
    private ShakeConfigurationFragment shakeConfigurationFragment;
    public void configure(SettingAlarmFragment settingAlarmFragment, Alarm alarm, ChallengeType currentChallengeType){
        this.listener = settingAlarmFragment;
        this.currentChallengeType = currentChallengeType;
        this.alarm = alarm;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        setControl(view);
        return view;
    }

    public void setControl(View view){
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

        layoutType = view.findViewById(R.id.layoutType);
        animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        layoutType.startAnimation(animFadein);

        mathConfigurationFragment = new MathConfigurationFragment();
        shakeConfigurationFragment = new ShakeConfigurationFragment();
        fragmentManager = getFragmentManager();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
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
                listener.getDefaultChallenge();
                updateChallengeLayoutColor(ChallengeType.DEFAULT);
            }
        });
        linearLayoutMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mathConfigurationFragment.configure(TypeFragment.this, alarm);
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container,
                        mathConfigurationFragment).addToBackStack(null).commit();
            }
        });
        linearLayoutShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shakeConfigurationFragment.configure(TypeFragment.this, alarm);
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container,
                        shakeConfigurationFragment).addToBackStack(null).commit();
            }
        });

        switch (currentChallengeType){
            case DEFAULT:
                linearLayoutDefault.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case MATH:
                linearLayoutMath.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case SHAKE:
                linearLayoutShake.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
        }
    }

    private void updateChallengeLayoutColor(ChallengeType newChallenge){
        switch (currentChallengeType){
            case DEFAULT:
                linearLayoutDefault.setBackgroundColor(getResources().getColor(R.color.challenge_layout_deactivate));
                break;
            case MATH:
                linearLayoutMath.setBackgroundColor(getResources().getColor(R.color.challenge_layout_deactivate));
                break;
            case SHAKE:
                linearLayoutShake.setBackgroundColor(getResources().getColor(R.color.challenge_layout_deactivate));
                break;
        }
        switch (newChallenge){
            case DEFAULT:
                linearLayoutDefault.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case MATH:
                linearLayoutMath.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
            case SHAKE:
                linearLayoutShake.setBackgroundColor(getResources().getColor(R.color.challenge_layout_activate));
                break;
        }
        currentChallengeType = newChallenge;
    }



    @Override
    public void onMathConfigurationSetup(MathDetail mathDetail) {
        this.listener.getMathChallenge(mathDetail);
        updateChallengeLayoutColor(ChallengeType.MATH);
    }

    @Override
    public void onShakeConfigurationSetup(ShakeDetail shakeDetail) {
        this.listener.getShakeChallenge(shakeDetail);
        updateChallengeLayoutColor(ChallengeType.SHAKE);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface TypeFragmentListener{
        void getDefaultChallenge();
        void getMathChallenge(MathDetail mathDetail);
        void getShakeChallenge(ShakeDetail shakeDetail);
    }
}
