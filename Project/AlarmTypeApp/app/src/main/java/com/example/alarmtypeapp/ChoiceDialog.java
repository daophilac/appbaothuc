package com.example.alarmtypeapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class ChoiceDialog extends DialogFragment {
    static ChoiceDialog newInstance() {
        return new ChoiceDialog();
    }
        @Override
    public Dialog onCreateDialog(Bundle bundle) {
        final String[] Colors ={"Red","Blue","Black"};
        AlertDialog.Builder dialogList = new AlertDialog.Builder(getActivity());
        //Set title
        dialogList.setTitle("Color");
        //Truyền danh sách colors vào, cài đặt sự kiện khi click vào Item
        dialogList.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Colors[item].toString();  //Lấy giá trị của Item
                Toast.makeText(getActivity(), selectedText, Toast.LENGTH_SHORT).show();
            }
        });
        return  dialogList.create();
    }
}
