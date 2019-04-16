package com.example.appbaothuc.alarmsetting;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appbaothuc.Alarm;
import com.example.appbaothuc.AlarmAdapter;
import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.MainActivity;
import com.example.appbaothuc.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingAlarmActivity extends AppCompatActivity implements LableDialogFragment.LabelDialogListener,
        AgainDialogFragment.AgainDialogListener, RepeatDialogFragment.RepeatDialogListener, View.OnClickListener {
    private TimePicker timePicker; // Chọn giờ
    private Button btnPlayMusic, btnCancel, btnDelete, btnOk; //Phát nhạc đang chọn, Hủy thao tác, Xóa báo thức, Hoàn tất
    private LinearLayout linearLayoutLabel, linearLayoutType, linearLayoutRingTone,
            linearLayoutRepeat, linearLayoutAgain;
    private TextView textViewPlus10M, textViewMinus10M, textViewPlus1H,
            textViewMinus1H;
    public TextView textViewTimeLeft /*thời gian còn lại*/, textViewType, textViewRepeat,
            textViewRingTone, textViewAgain, textViewLabel;
    private ImageView imageViewType; //cái hình điện thoại rung
    private SeekBar seekBar; // thanh âm lượng
    private Switch aSwitch; // bật tắt rung

    public static YourRingTone yourRingTone;
    public static String label, outputAgain, outputRepeat;
    public static int hour, minute, snoozeTime, volume;
    public static ArrayList<Boolean> listRepeatDay;
    public static boolean vibrate;
    public static int challengeType; // chua co

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm);
        setControll();
    }

    void setControll() {
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnOk = findViewById(R.id.btnOk);
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

        textViewMinus1H.setOnClickListener(this); // event trong hàm onClick()
        textViewMinus10M.setOnClickListener(this);
        textViewPlus1H.setOnClickListener(this);
        textViewPlus10M.setOnClickListener(this);

        btnOk.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                outputAgain = textViewAgain.getText().toString();
                label = textViewLabel.getText().toString();
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                volume = seekBar.getProgress();
                if (aSwitch.isChecked()) vibrate = true;
                else vibrate = false;
                String tst = label + " _ " + outputAgain + " _ "
                        + hour + " _ " + minute;

                Toast.makeText(SettingAlarmActivity.this, tst, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        linearLayoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingAlarmActivity.this, TypeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
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

        linearLayoutRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRepeatDialog();
            }
        });
        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        int timeHour = timePicker.getHour();
        int timeMinute = timePicker.getMinute();
        switch (view.getId()) {
            case R.id.textViewMinus1H:  // bấm trừ 1 giờ
                if (timeHour == 0) timePicker.setHour(23);
                else timePicker.setHour(timeHour - 1);
                break;
            case R.id.textViewPlus1H:   // bấm cộng 1 giờ
                if (timeHour == 23) timePicker.setHour(0);
                else timePicker.setHour(timeHour + 1);
                break;
            case R.id.textViewMinus10M: // Bấm trừ 10 phút
                if (timeMinute - 10 < 0) {   // nếu trừ 10 phút mà ra số âm thì phải trừ giờ xuống 1.
                    if (timeHour == 0) timePicker.setHour(23);
                    else timePicker.setHour(timeHour - 1);
                    timePicker.setMinute(60 - (10 - timeMinute));   // phút thì còn tầm 5 mươi mấy đó theo công thức
                } else timePicker.setMinute(timeMinute - 10);
                break;
            case R.id.textViewPlus10M:
                if (timeMinute + 10 > 59) {
                    if (timeHour == 23) timePicker.setHour(0);
                    else timePicker.setHour(timeHour + 1);
                    timePicker.setMinute(10 + timeMinute - 60);
                } else timePicker.setMinute(timeMinute + 10);
                break;
        }
    }

    private void showLableDialog() { // show fragment để Sửa tên báo thức

        LableDialogFragment lableDialogFragment = new LableDialogFragment();
        lableDialogFragment.show(getSupportFragmentManager(), "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText) { //get text ở label dialog fragment
        textViewLabel.setText(inputText);
    }


    private void showAgainDialog() { // fragment chọn thời gian báo thức lại
        AgainDialogFragment againDialogFragment = new AgainDialogFragment();
        againDialogFragment.show(getSupportFragmentManager(), "fragment_choice");
    }

    @Override
    public void onFinishChoiceDialog(Integer input) {
        snoozeTime = input;
        if (input == 0) textViewAgain.setText("Tắt");
        else textViewAgain.setText(input + " Phút.");
    }

    private void showRepeatDialog() { // fragment chọn các ngày báo thức

        RepeatDialogFragment repeatDialogFragment = new RepeatDialogFragment();
        repeatDialogFragment.show(getSupportFragmentManager(), "fragment_repeat");
    }

    @Override
    public void onFinishCheckDialog(ArrayList<Boolean> input) {
        listRepeatDay = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            listRepeatDay.add(i, false);
            listRepeatDay.set(i, input.get(i));
        }
        createStringRepeat(input);
    }

    public void createStringRepeat(ArrayList<Boolean> listDays) {
        String repeatString = "";
        int i = 0;
        for (i = 0; i < 7; i++) {
            if (listDays.get(i) == false) break;
        }
        if (i == 7) {
            outputRepeat = "Hằng ngày.";
            textViewRepeat.setText(outputRepeat);
            return;
        } else if (i == 5) {
            outputRepeat = "Các ngày làm việc.";
            textViewRepeat.setText(outputRepeat);
            return;
        }
        i = 0;
        for (i = 0; i < 5; i++) {
            if (listDays.get(i) == true) break;
        }
        if (i == 5 && listDays.get(5) == true && listDays.get(6) == true) {
            outputRepeat = "Cuối tuần.";
            textViewRepeat.setText(outputRepeat);
            return;
        } else {
            for (int j = 0; j < 7; j++) {
                if (listDays.get(j) == true) {
                    if (j == 6) repeatString += " CN";
                    else repeatString += " T" + (j + 2);
                }
            }
            outputRepeat = repeatString;
            textViewRepeat.setText(outputRepeat);
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    static boolean play = false;

    public void getMusic() {
        Map<String, String> list = new HashMap<>();
        list = getNotifications();
        String a = "";
        String b = "";
        for (HashMap.Entry<String, String> h : list.entrySet()) {
            b = h.getValue();
            a += h.getKey();
            a += h.getValue() + "\n";
        }


    }

    public void playMusic() {
        getMusic();


        if (play == true) {
            play = false;
            btnPlayMusic.setBackgroundResource(R.drawable.ic_play_arrow_24dp);
        } else {
            try {

                Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
                Ringtone defaultRingtone = RingtoneManager.getRingtone(getApplicationContext(), defaultRintoneUri);
                defaultRingtone.play();
//                Ringtone defaultRingtone = RingtoneManager.getRingtone(getApplicationContext(), Uri.parse("content://media/internal/audio/media/156"));
//                defaultRingtone.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            play = true;
            btnPlayMusic.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }
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

    public Uri[] getListRingTone() {
        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = ringtoneMgr.getCursor();
        int alarmsCount = alarmsCursor.getCount();
        if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
            return null;
        }
        Uri[] alarms = new Uri[alarmsCount];
        while (!alarmsCursor.isAfterLast() && alarmsCursor.moveToNext()) {
            int currentPosition = alarmsCursor.getPosition();
            alarms[currentPosition] = ringtoneMgr.getRingtoneUri(currentPosition);
        }
        alarmsCursor.close();


        Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        Ringtone defaultRingtone = RingtoneManager.getRingtone(getApplicationContext(), defaultRintoneUri);
        defaultRingtone.play();
        return alarms;
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
