package com.example.alarmtypeapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LableDialogFragment.EditNameDialogListener {
    @Override
    public void onFinishEditDialog(String inputText) {
        textViewLabel.setText(inputText);
    }

    private TimePicker timePicker;
    private Button btnPlayMusic, btnCancel, btnDelete, btnOk;
    private LinearLayout linearLayoutLabel, linearLayoutType, linearLayoutRingTone,
            linearLayoutRepeat, linearLayoutAgain;
    public TextView textViewTimeLeft, textViewPlus10M, textViewMinus10M, textViewPlus1H,
            textViewMinus1H, textViewType, textViewRepeat, textViewRingTone, textViewAgain,
            textViewLabel;
    private ImageView imageViewType;
    private SeekBar seekBar;
    private Switch aSwitch;
    public EditText txt_your_name;
    public static YourRingTone yourRingTone;

    public static String stringLabel, stringAgaint;
    public TextView textViewLabelString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControll();
    }
    void setControll(){
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnOk = findViewById(R.id.btnOk);
        txt_your_name = findViewById(R.id.txt_your_name);
        linearLayoutType = findViewById(R.id.linearLayoutType);
        linearLayoutRingTone = findViewById(R.id.linearLayoutRingTone);
        linearLayoutRepeat = findViewById(R.id.linearLayoutRepeat);
        linearLayoutAgain = findViewById(R.id.linearLayoutAgain);
        linearLayoutLabel = findViewById(R.id.linearLayoutLabel);

        textViewTimeLeft = findViewById(R.id.textViewTimeLeft);
        textViewPlus10M = findViewById(R.id.textViewPlus10M);
        textViewMinus10M = findViewById(R.id.textViewMinus10M);
        textViewPlus1H = findViewById(R.id.textViewPlus1H);
        textViewMinus1H = findViewById(R.id.textViewMinus1H);
        textViewType = findViewById(R.id.textViewType);
        textViewRepeat = findViewById(R.id.textViewRepeat);
        textViewRingTone = findViewById(R.id.textViewRingTone);
        textViewAgain = findViewById(R.id.textViewAgain);
        textViewLabel = findViewById(R.id.textViewLabel);

        imageViewType = findViewById(R.id.imageViewType);
        seekBar = findViewById(R.id.seekBar);
        aSwitch = findViewById(R.id.aSwitch);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

       // textViewLabelString = findViewById(R.id.txt_your_name);

        linearLayoutLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLableDialog();
            }
        });

        linearLayoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAgainDialog();
            }
        });
    }
    private void showLableDialog() {

        LableDialogFragment lableDialogFragment = new LableDialogFragment();
        lableDialogFragment.show(getSupportFragmentManager(), "fragment_edit_name");
    }
    private void showAgainDialog() {
        AgainDialogFragment againDialogFragment = new AgainDialogFragment();
        againDialogFragment.show(getSupportFragmentManager(), "fragment_choice");
    }

    protected void onResume() {

        super.onResume();
        //textViewLabel.setText(textViewLabelString.getText());
    }

//
    static boolean play = false;
    public void getMusic(){
        Map<String, String> list = new HashMap<>();
        list = getNotifications();
        String a = "";
        String b = "";
        for(HashMap.Entry<String, String> h : list.entrySet())
        { b = h.getValue();
            a += h.getKey();
            a+= h.getValue() + "\n";
        }



    }
    public void playMusic(){



        if(play == true){
            play = false;
            btnPlayMusic.setBackgroundResource(R.drawable.ic_play_arrow_24dp);
        }
        else {
            try {
                PlayAudioManager.playAudio(this, "content://media/internal/audio/media/104");
            } catch (Exception e) {
                e.printStackTrace();
            }
            play = true;
            btnPlayMusic.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }
        // TODO...

    }


    public Map<String, String> getNotifications() {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationUri, notificationTitle);
        }
        return list;
    }
}

class PlayAudioManager {
    private static MediaPlayer mediaPlayer;

    public static void playAudio(final Context context, final String url) throws Exception {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                killMediaPlayer();
            }
        });
        mediaPlayer.start();
    }

    private static void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

//    public Uri[] getListRingTone(){
//        RingtoneManager ringtoneMgr = new RingtoneManager(this);
//        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
//        Cursor alarmsCursor = ringtoneMgr.getCursor();
//        int alarmsCount = alarmsCursor.getCount();
//        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
//            return null;
//        }
//        Uri[] alarms = new Uri[alarmsCount];
//        while(!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
//            int currentPosition = alarmsCursor.getPosition();
//            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);
//        }
//        alarmsCursor.close();
//        return alarms;
//    }


//        Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
//        Ringtone defaultRingtone = RingtoneManager.getRingtone(getApplicationContext(), defaultRintoneUri);
//        defaultRingtone.play();