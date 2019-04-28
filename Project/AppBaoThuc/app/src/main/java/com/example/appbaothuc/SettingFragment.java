package com.example.appbaothuc;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbaothuc.alarmsetting.MusicPickerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingFragment extends Fragment {
    // TODO: Khai báo các biến public quy định setting của app ở đây. Ví dụ:
    // TODO: public boolean maxVolume;
    // TODO: public boolean preventPhoneTurnOff;
    // TODO: public int maxSnooze;

    public static int muteMusicIn; // how many seconds?
    public static int canMuteMusicFor; // how many times?
    public static boolean graduallyIncreaseVolume;
    public static boolean preventTurnOffPhone;
    public static int dismissAfter;
    //public static MusicPickerFragment.ChooseMusicType chooseMusicType = MusicPickerFragment.ChooseMusicType.MUSIC;
    public static HourMode hourMode; //12h or 24h
    public static List<String> listRingtoneDirectory;
    public static SettingFragment newInstance(){
        // TODO: This approach won't guarantee that the setting is created if the developer
        // have not called this method to create a new instance of the class, but use the
        // default constructor instead.
        // And the other downside is it can only provide the same setting each time the app launches.
        // But we keep doing this for simplicity;
        SettingFragment settingFragment = new SettingFragment();
        muteMusicIn = 30;
        canMuteMusicFor = 3;
        graduallyIncreaseVolume = true;
        preventTurnOffPhone = true;
        dismissAfter = 30;
        listRingtoneDirectory = new ArrayList<>();
        listRingtoneDirectory.add("/sdcard/music");
        listRingtoneDirectory.add("/sdcard/download");
        return settingFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // TODO: Khởi tạo giá trị cho các biến ở đây, ví dụ button = view.findViewById(R.id.button);
        // TODO: Sau đó viết các phương thức xử lý thao tác của người dùng, rồi lưu kết quả vào các thuộc tính public
        return view;
    }
    enum HourMode{
        H12, H24
    }
}