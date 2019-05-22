package com.example.appbaothuc.challenge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.services.MusicPlayerService;

import static com.example.appbaothuc.appsetting.AppSettingFragment.canMuteAlarmFor;
import static com.example.appbaothuc.appsetting.AppSettingFragment.muteAlarmIn;
import static com.example.appbaothuc.models.ChallengeType.DEFAULT;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.MUTE_A_LITTLE;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand.RESUME;


public class ChallengeDialogFragment extends DialogFragment {
    private boolean debugMode = true; // TODO: remove this when release
    private OnSaveChallengeState onSaveChallengeState;
    private DatabaseHandler databaseHandler;
    private Alarm alarm;

    private TextView textViewLabel;
    private TextView textViewRingtoneName;
    private TextView textViewHour;
    private TextView textViewMinute;
    private ImageButton buttonMute;
    private Button buttonGiveUp;

    private Animation animationTextViewBlink5Times;
    private TextView textViewCountDownReason;
    private TextView textViewCountDownMinute;
    private TextView textViewCountDownColon;
    private TextView textViewCountDownSecond;
    private TextView textViewCountDownDot;
    private TextView textViewCountDownMillisecond;

    private FragmentManager fragmentManager;
    private DefaultChallengeFragment defaultChallengeFragment;
    private MathChallengeFragment mathChallengeFragment;
    private ShakeChallengeFragment shakeChallengeFragment;
    private GiveUpDialogFragment giveUpDialogFragment;

    private CountDownTimer countDownTimer;
    private int muteTime;
    private long millisecondLeft;
    private boolean isMuting;

    public ChallengeDialogFragment() { }

    public static ChallengeDialogFragment newInstance(Alarm alarm, String title) {
        ChallengeDialogFragment challengeDialogFragment = new ChallengeDialogFragment();
        challengeDialogFragment.setAlarm(alarm);
        challengeDialogFragment.setCancelable(false);
        return challengeDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        databaseHandler = new DatabaseHandler(context);
        giveUpDialogFragment = GiveUpDialogFragment.newInstance();
        animationTextViewBlink5Times = new AlphaAnimation(0f, 1f);
        animationTextViewBlink5Times.setDuration(500);
        animationTextViewBlink5Times.setRepeatCount(9);
        animationTextViewBlink5Times.setRepeatMode(Animation.REVERSE);
        animationTextViewBlink5Times.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textViewCountDownReason.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isMuting){
            countDownTimer.cancel();
        }
    }

    private void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundleChallenge = getArguments();
        View view = inflater.inflate(R.layout.fragment_challenge_dialog, container, false);
        textViewLabel = view.findViewById(R.id.text_view_label);
        textViewRingtoneName = view.findViewById(R.id.text_view_ringtone_name);
        textViewHour = view.findViewById(R.id.textviewHour);
        textViewMinute = view.findViewById(R.id.textviewMinute);
        buttonGiveUp = view.findViewById(R.id.button_give_up);
        buttonMute = view.findViewById(R.id.button_mute);
        textViewCountDownReason = view.findViewById(R.id.text_view_count_down_reason);
        textViewCountDownMinute = view.findViewById(R.id.text_view_count_down_minute);
        textViewCountDownColon = view.findViewById(R.id.text_view_count_down_colon);
        textViewCountDownSecond = view.findViewById(R.id.text_view_count_down_second);
        textViewCountDownDot = view.findViewById(R.id.text_view_count_down_dot);
        textViewCountDownMillisecond = view.findViewById(R.id.text_view_count_down_millisecond);


        if(alarm.getChallengeType() == DEFAULT){
            buttonGiveUp.setVisibility(View.INVISIBLE);
        }
        else{
            buttonGiveUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    giveUpDialogFragment.show(fragmentManager, null);
                }
            });
        }
        if(!alarm.getLabel().equals("null")){
            textViewLabel.setText(alarm.getLabel());
        }
        textViewHour.setText(String.valueOf(alarm.getHour()));
        textViewRingtoneName.setText("Music: " + alarm.getRingtone().getName());
        if (alarm.getMinute() < 10) {
            textViewMinute.setText("0" + alarm.getMinute());
        } else {
            textViewMinute.setText(String.valueOf(alarm.getMinute()));
        }
        buttonMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canMuteAlarmFor == 0){
                    if(animationTextViewBlink5Times.hasEnded() || !animationTextViewBlink5Times.hasStarted()){
                        textViewCountDownReason.setText("Based on your setting, you cannot mute the alarm");
                        textViewCountDownReason.startAnimation(animationTextViewBlink5Times);
                    }
                    return;
                }
                if(muteTime >= canMuteAlarmFor){
                    if(animationTextViewBlink5Times.hasEnded() || !animationTextViewBlink5Times.hasStarted()){
                        textViewCountDownReason.setText("You cannot mute the alarm for more than " + canMuteAlarmFor + " times");
                        textViewCountDownReason.startAnimation(animationTextViewBlink5Times);
                    }
                    return;
                }
                if(isMuting){
                    return;
                }
                Toast.makeText(getContext(), "Mute alarm for " + muteAlarmIn + " seconds", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MusicPlayerService.class);
                intent.putExtra("command", MUTE_A_LITTLE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    getContext().startForegroundService(intent);
                    getContext().startService(intent);
                }
                else{
                    getContext().startService(intent);
                }
                createCountDownTask();
            }
        });
        if(bundleChallenge != null){
            muteTime = bundleChallenge.getInt("muteTime");
            millisecondLeft = bundleChallenge.getLong("millisecondLeft");
            isMuting = bundleChallenge.getBoolean("isMuting");
            if(isMuting){
                createCountDownTask();
            }
        }
        else{
            muteTime = 0;
            millisecondLeft = muteAlarmIn * 1000;
            isMuting = false;
        }
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getChildFragmentManager();
        switch (alarm.getChallengeType()) {
            case DEFAULT:
                defaultChallengeFragment = new DefaultChallengeFragment();
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, defaultChallengeFragment).commit();
                break;
            case MATH:
                mathChallengeFragment = new MathChallengeFragment();
                mathChallengeFragment.setMathDetail(databaseHandler.getAlarmMathDetail(alarm.getIdAlarm()));
                mathChallengeFragment.setArguments(bundleChallenge);
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, mathChallengeFragment).commit();
                break;
            case SHAKE:
                shakeChallengeFragment = new ShakeChallengeFragment();
                shakeChallengeFragment.setShakeDetail(databaseHandler.getAlarmShakeDetail(alarm.getIdAlarm()));
                shakeChallengeFragment.setArguments(bundleChallenge);
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, shakeChallengeFragment).commit();
                break;
            case WALK:
                throw new RuntimeException("must implement");
        }
        return view;
    }
    private void createCountDownTask(){
        isMuting = true;
        if(muteAlarmIn > 60){
            final long[] minuteLeft = {millisecondLeft / 60000};
            final long[] secondLeft = {millisecondLeft / 1000};
            final long[] millisecondMinuteThreshold = {minuteLeft[0] * 60000};
            final long[] millisecondSecondThreshold = {secondLeft[0] * 1000};
            textViewCountDownMinute.setText(String.valueOf(minuteLeft[0]));
            textViewCountDownColon.setText(":");
            textViewCountDownSecond.setText(String.format("%02d", secondLeft[0] % 60));
            textViewCountDownDot.setText(".");
            textViewCountDownMillisecond.setText(String.format("%03d", millisecondLeft % 1000));
            countDownTimer = new CountDownTimer(millisecondLeft, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished < millisecondMinuteThreshold[0]){
                        minuteLeft[0]--;
                        millisecondMinuteThreshold[0]-=60000;

                        textViewCountDownMinute.setText(String.valueOf(minuteLeft[0]));
                    }
                    if(millisUntilFinished < millisecondSecondThreshold[0]){
                        secondLeft[0]--;
                        millisecondSecondThreshold[0]-=1000;
                        textViewCountDownSecond.setText(String.format("%02d", secondLeft[0] % 60));
                    }
                    millisecondLeft = millisUntilFinished;
                    textViewCountDownMillisecond.setText(String.format("%03d", millisUntilFinished % 1000));
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getContext(), MusicPlayerService.class);
                    intent.putExtra("command", RESUME);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        getContext().startForegroundService(intent);
                        getContext().startService(intent);
                    }
                    else{
                        getContext().startService(intent);
                    }
                    textViewCountDownMinute.setText("");
                    textViewCountDownColon.setText("");
                    textViewCountDownSecond.setText("");
                    textViewCountDownDot.setText("");
                    textViewCountDownMillisecond.setText("");
                    isMuting = false;
                    muteTime++;
                    millisecondLeft = muteAlarmIn * 1000;
                }
            };
            countDownTimer.start();
        }
        else{
            final long[] secondLeft = {millisecondLeft / 1000};
            final long[] millisecondSecondThreshold = {secondLeft[0] * 1000};
            textViewCountDownSecond.setText(String.format("%02d", secondLeft[0] % 60));
            textViewCountDownDot.setText(".");
            textViewCountDownMillisecond.setText(String.format("%03d", millisecondLeft % 1000));
            countDownTimer = new CountDownTimer(millisecondLeft, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(millisUntilFinished < millisecondSecondThreshold[0]){
                        secondLeft[0]--;
                        millisecondSecondThreshold[0]-=1000;
                        textViewCountDownSecond.setText(String.format("%02d", secondLeft[0] % 60));
                    }
                    millisecondLeft = millisUntilFinished;
                    textViewCountDownMillisecond.setText(String.format("%03d", millisUntilFinished % 1000));
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getContext(), MusicPlayerService.class);
                    intent.putExtra("command", RESUME);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        getContext().startForegroundService(intent);
                        getContext().startService(intent);
                    }
                    else{
                        getContext().startService(intent);
                    }
                    textViewCountDownSecond.setText("");
                    textViewCountDownDot.setText("");
                    textViewCountDownMillisecond.setText("");
                    isMuting = false;
                    muteTime++;
                    millisecondLeft = muteAlarmIn * 1000;
                }
            };
            countDownTimer.start();
        }
    }

    Bundle buildBundleForRecreation(){
        if(onSaveChallengeState == null){
            return null;
        }
        Bundle result = onSaveChallengeState.onSaveChallengeState();
        result.putInt("muteTime", muteTime);
        result.putLong("millisecondLeft", millisecondLeft);
        result.putBoolean("isMuting", isMuting);
        return result;
    }

    public void setOnSaveChallengeState(OnSaveChallengeState onSaveChallengeState) {
        this.onSaveChallengeState = onSaveChallengeState;
    }

    public interface OnSaveChallengeState{
        Bundle onSaveChallengeState();
    }
}