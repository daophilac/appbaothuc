package com.example.appbaothuc.challenge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.R;
import com.example.appbaothuc.listeners.ChallengeActivityListener;
import com.example.appbaothuc.listeners.ChallengeDialogListener;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.services.MusicPlayerService;

import static com.example.appbaothuc.challenge.ChallengeActivity.ChallengeType;
import static com.example.appbaothuc.services.MusicPlayerService.AlarmMusicPlayerCommand;


public class ChallengeDialogFragment extends DialogFragment implements GiveUpDialogFragment.OnGiveUpListener {
    private boolean debugMode = true; // TODO: remove this when release
    private DatabaseHandler databaseHandler;
    private Alarm alarm;

    private TextView textViewLabel;
    private TextView textViewRingtoneName;
    private TextView textViewHour;
    private TextView textViewMinute;
    private ImageButton buttonMute;
    private Button buttonGiveUp;

    private FragmentManager fragmentManager;
//    private MathChallengeFragment mathChallengeFragment;
    private DefaultChallengeFragment defaultChallengeFragment;
    private MathChallengeFragment mathChallengeFragment;
    private ShakeChallengeFragment shakeChallengeFragment;
    private GiveUpDialogFragment giveUpDialogFragment;
    private ChallengeDialogListener challengeDialogListener;

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
        this.challengeDialogListener = (ChallengeDialogListener) context;
        this.databaseHandler = new DatabaseHandler(context);
        this.fragmentManager = getChildFragmentManager();
        this.giveUpDialogFragment = GiveUpDialogFragment.newInstance();
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
                Intent intent = new Intent(getContext(), MusicPlayerService.class);
                intent.putExtra("command", AlarmMusicPlayerCommand.MUTE_A_LITTLE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    getContext().startForegroundService(intent);
                    getContext().startService(intent);
                }
                else{
                    getContext().startService(intent);
                }
            }
        });
        if(alarm.getChallengeType() == ChallengeType.DEFAULT){
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
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
                    return true;
                }
                return false;
            }
        });

        switch (alarm.getChallengeType()) {
            case DEFAULT:
                defaultChallengeFragment = new DefaultChallengeFragment();
                challengeDialogListener.onChallengeActivated(defaultChallengeFragment);
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, defaultChallengeFragment).commit();
                break;
            case MATH:
                mathChallengeFragment = new MathChallengeFragment();
                mathChallengeFragment.setMathDetail(databaseHandler.getAlarmMathDetail(alarm.getIdAlarm()));
                challengeDialogListener.onChallengeActivated(mathChallengeFragment);
                mathChallengeFragment.setArguments(bundleChallenge);
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, mathChallengeFragment).commit();
                break;
            case SHAKE:
                shakeChallengeFragment = new ShakeChallengeFragment();
                shakeChallengeFragment.setShakeDetail(databaseHandler.getAlarmShakeDetail(alarm.getIdAlarm()));
                challengeDialogListener.onChallengeActivated(shakeChallengeFragment);
                shakeChallengeFragment.setArguments(bundleChallenge);
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, shakeChallengeFragment).commit();
                break;
            case WALK:
                throw new RuntimeException("must implement");
        }
        return view;
    }

    @Override
    public void onGaveUp() {
        ChallengeActivityListener challengeActivityListener = (ChallengeActivityListener) getActivity();
        challengeActivityListener.onFinishChallenge();
    }
}