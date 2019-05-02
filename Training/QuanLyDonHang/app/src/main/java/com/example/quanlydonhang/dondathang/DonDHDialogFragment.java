package com.example.quanlydonhang.dondathang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;
import com.example.quanlydonhang.chitietdondathang.CTDonDHActivity;

public class DonDHDialogFragment extends DialogFragment {
    private Button buttonUpdateDDH, buttonDeleteDDH;
    private TextView textViewSoDDHDialog, textViewMaKHDialog;

    private TextView textViewSoDDHEdit;
    private EditText editTextMaKHEdit, editTextNgayDHEdit, editTextSoNgayEdit, editTextTinhTrangEdit;
    public DonDHDialogFragment(){
    }
    public interface DDHDialogListener {
        void onFinishDDHHDialog(int input);
    }
    private DDHDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_don_dh, container);
        buttonUpdateDDH = view.findViewById(R.id.buttonUpdateDDH);
        buttonDeleteDDH = view.findViewById(R.id.buttonDeleteDDH);
        textViewSoDDHDialog = view.findViewById(R.id.textViewSoDDHDialog);
        textViewMaKHDialog = view.findViewById(R.id.textViewMaKHDialog);

        textViewSoDDHDialog.setText("Số Đơn Đặt Hàng: " + DonDHActivity.donDH.getSoDH());
        textViewMaKHDialog.setText("Mã Khách Hàng: " + DonDHActivity.donDH.getMaKH());

        //listener.onFinishDDHHDialog(0);

        buttonUpdateDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishDDHHDialog(1);
                getDialog().dismiss();
            }
        });

        buttonDeleteDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                if(databaseHandler.getCTDonDH(DonDHActivity.donDH.getSoDH())){
                    Toast.makeText(getActivity(),"Đơn hàng này đã có chi tiết đơn đặt hàng, không được xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler db = new DatabaseHandler(getContext());
                                db.deleteDonDH(DonDHActivity.donDH.getSoDH());
                                db.close();
                                listener.onFinishDDHHDialog(0);
                                getDialog().dismiss();
                                Toast.makeText(getActivity(),"Delete thành công!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do nothing.
                            }
                        }).show();

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (DDHDialogListener) context;
    }
}
