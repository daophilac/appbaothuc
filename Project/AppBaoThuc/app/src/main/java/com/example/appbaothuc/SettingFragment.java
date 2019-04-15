package com.example.appbaothuc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // TODO: Khởi tạo giá trị cho các biến ở đây, ví dụ button = view.findViewById(R.id.button);
        // TODO: Sau đó viết các phương thức xử lý thao tác của người dùng, rồi lưu kết quả vào các thuộc tính public
        return view;
    }
}