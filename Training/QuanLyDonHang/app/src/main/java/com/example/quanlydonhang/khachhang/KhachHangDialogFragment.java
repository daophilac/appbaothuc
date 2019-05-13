package com.example.quanlydonhang.khachhang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;

public class KhachHangDialogFragment extends DialogFragment {
    private Button buttonUpdateKH, buttonDeleteKH, buttonKHMuaNhieuNhat;
    private TextView textViewMaHKDialog, textViewTenKHDialog;

    public KhachHangDialogFragment(){
    }
    public interface KhachHangDialogListener {
        void onFinishKHDialog(int input);
    }
    private KhachHangDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_khach_hang, container);
        buttonUpdateKH = view.findViewById(R.id.buttonUpdateKH);
        buttonDeleteKH = view.findViewById(R.id.buttonDeleteKH);
        buttonKHMuaNhieuNhat = view.findViewById(R.id.buttonKHMuaNhieuNhat);
        textViewMaHKDialog = view.findViewById(R.id.textViewMaHKDialog);
        textViewTenKHDialog = view.findViewById(R.id.textViewTenKHDialog);


        textViewMaHKDialog.setText("Mã Khách Hàng: " + KhachHangActivity.khachHang.getMaKH());
        textViewTenKHDialog.setText("Tên Khách Hàng: " + KhachHangActivity.khachHang.getTenKH());

        //listener.onFinishKHDialog(0);

        buttonUpdateKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishKHDialog(1);
                getDialog().dismiss();
            }
        });

        buttonDeleteKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                if(databaseHandler.getDDH(KhachHangActivity.khachHang.getMaKH())){
                    Toast.makeText(getActivity(),"Khách hàng này đã có đơn đặt hàng, không được xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler db = new DatabaseHandler(getContext());
                                db.deleteKhachHang(KhachHangActivity.khachHang.getMaKH());
                                db.close();
                                listener.onFinishKHDialog(0);
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
        buttonKHMuaNhieuNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishKHDialog(2);
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (KhachHangDialogListener) context;
    }
}
