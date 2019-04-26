package com.example.quanlydonhang.chitietdondathang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;

public class CTDonDHDialogfragment extends DialogFragment {
    private Button buttonUpdateCTDonDH, buttonDeleteCTDonDH;
    private TextView textViewSoDDHDialogCT, textViewMaMHDialogCT;
    public CTDonDHDialogfragment(){
    }
    public interface CTDonDHDialogListener {
        void onFinishCTDonDHDialog(int input);
    }
    private CTDonDHDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_ct_don_dh, container);
        buttonUpdateCTDonDH = view.findViewById(R.id.buttonUpdateCTDonDH);
        buttonDeleteCTDonDH = view.findViewById(R.id.buttonDeleteCTDonDH);
        textViewSoDDHDialogCT = view.findViewById(R.id.textViewSoDDHDialogCT);
        textViewMaMHDialogCT = view.findViewById(R.id.textViewMaMHDialogCT);


        textViewSoDDHDialogCT.setText("Số Đơn Đặt Hàng: " + CTDonDHActivity.ctDonDH.getSODDH());
        textViewMaMHDialogCT.setText("Mã Mặt Hàng: " + CTDonDHActivity.ctDonDH.getMAHG());

        //listener.onFinishCTDonDHDialog(0);

        buttonUpdateCTDonDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishCTDonDHDialog(1);
                getDialog().dismiss();
            }
        });

        buttonDeleteCTDonDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler db = new DatabaseHandler(getContext());
                                db.deleteCTDonDH(CTDonDHActivity.ctDonDH.getSODDH(), CTDonDHActivity.ctDonDH.getMAHG());
                                db.close();
                                listener.onFinishCTDonDHDialog(0);
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
        this.listener = (CTDonDHDialogListener) context;
    }
}
