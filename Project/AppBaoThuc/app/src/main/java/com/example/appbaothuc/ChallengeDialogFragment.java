package com.example.appbaothuc;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


// TODO: WARNING: This class has some high logical handles
enum ChallengeType{
    Math, Shake, Qrcode
}
public class ChallengeDialogFragment extends DialogFragment implements MathChallengeFragment.OnFinishChallengeListener{
    private MediaPlayer mediaPlayer;
    private String musicFilePath = "/sdcard/download/boss battle a.flac"; //TODO: Hard-coded
    private ChallengeType challengeType = ChallengeType.Math; // TODO: Hard-coded
    private boolean graduallyIncreaseVolume = true; //TODO: Hard-coded
    private boolean maxVolume = true; //TODO: Hard-coded
    private int snoozeTime = 5; //TODO: Hard-coded
    private ImageButton buttonSnooze;
    private Button buttonOk;
    private Button buttonCancel;

    private FragmentManager fragmentManager;
    private MathChallengeFragment mathChallengeFragment;

    // flags for communication with background threads
    private boolean isDismissed = false;
    private boolean isSnoozing = false;
    private boolean snoozeAgain = false;
    private Thread threadSnooze;

    public ChallengeDialogFragment(){}
    public static ChallengeDialogFragment newInstance(String title){
        ChallengeDialogFragment challengeDialogFragment = new ChallengeDialogFragment();
        challengeDialogFragment.setCancelable(false);
        return challengeDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_dialog, container, false);
        fragmentManager = getChildFragmentManager();
        switch(challengeType){
            case Math:
                mathChallengeFragment = new MathChallengeFragment();
                fragmentManager.beginTransaction().replace(R.id.challenge_fragment_container, mathChallengeFragment).commit();
                break;
            default:
                break;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonOk = view.findViewById(R.id.button_ok);
        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonSnooze = view.findViewById(R.id.button_snooze);

        buttonSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Snooze for " + String.valueOf(snoozeTime) + " seconds.", Toast.LENGTH_LONG).show();
                SnoozeManager snoozeManager = new SnoozeManager(mediaPlayer, snoozeTime);
                if(threadSnooze == null || !threadSnooze.isAlive()){
                    threadSnooze = new Thread(snoozeManager);
                    threadSnooze.start();
                }
                else{
                    snoozeAgain = true;
                }
            }
        });




        if(maxVolume){
            AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

            am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.boss_battle_a); //TODO
        mediaPlayer.setLooping(true);
        if(!graduallyIncreaseVolume){
            mediaPlayer.start();
        }
        else{
            mediaPlayer.setVolume(0,0);
            mediaPlayer.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(float i = 1; i <= 1000; i++){
                        try {
                            if(isDismissed){
                                return;
                            }
                            if(isSnoozing){
                                return;
                            }
                            mediaPlayer.setVolume(i / 1000,i / 1000);
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
    private class SnoozeManager implements Runnable{
        private MediaPlayer mediaPlayer;
        private int snoozeTime;
        public SnoozeManager(MediaPlayer mediaPlayer, int snoozeTime){
            this.mediaPlayer = mediaPlayer;
            this.snoozeTime = snoozeTime;
        }
        public void run(){
            isSnoozing = true;
            mediaPlayer.setVolume(0,0);
            try {
                Thread.sleep(this.snoozeTime * 1000);
                isSnoozing = false;
                for(float i = 1; i <= 1000; i++){
                    if(isDismissed){
                        return;
                    }
                    if(snoozeAgain){
                        snoozeAgain = false;
                        run();
                        return;
                    }
                    mediaPlayer.setVolume(i / 1000,i / 1000);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinishChallenge() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmService.class);
                getActivity().stopService(intent);
                isDismissed = true;
                mediaPlayer.release();
                getDialog().dismiss();
                getActivity().finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmService.class);
                getActivity().stopService(intent);
                isDismissed = true;
                mediaPlayer.release();
                getDialog().dismiss();
                getActivity().finish();
            }
        });
    }
}