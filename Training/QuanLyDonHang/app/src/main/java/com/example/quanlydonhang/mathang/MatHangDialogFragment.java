package com.example.quanlydonhang.mathang;

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

public class MatHangDialogFragment extends DialogFragment {
    private Button buttonViewCTDDHMH, buttonUpdateMatHang, buttonDeleteMatHang;
    private TextView textViewMaMHDialog, textViewTenMHDialog;

    public MatHangDialogFragment(){
    }
    public interface MatHangDialogListener {
        void onFinishMatHangDialog(int input);
    }
    private MatHangDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_mat_hang, container);
        buttonViewCTDDHMH = view.findViewById(R.id.buttonViewCTDDHMH);
        buttonUpdateMatHang = view.findViewById(R.id.buttonUpdateMatHang);
        buttonDeleteMatHang = view.findViewById(R.id.buttonDeleteMatHang);
        textViewMaMHDialog = view.findViewById(R.id.textViewMaMHDialog);
        textViewTenMHDialog = view.findViewById(R.id.textViewTenMHDialog);


        textViewMaMHDialog.setText("Mã Mặt Hàng: " + MatHangActivity.matHang.getMAHG());
        textViewTenMHDialog.setText("Tên Mặt Hàng: " + MatHangActivity.matHang.getTENHG());

        listener.onFinishMatHangDialog(0);

        buttonUpdateMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishMatHangDialog(1);
                getDialog().dismiss();
            }
        });

        buttonDeleteMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                if(databaseHandler.getCTDonDH(MatHangActivity.matHang.getMAHG())){
                    Toast.makeText(getActivity(),"Mặt hàng này đã có chi tiết đơn đặt hàng, không được xóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseHandler db = new DatabaseHandler(getContext());
                                db.deleteMatHang(MatHangActivity.matHang.getMAHG());
                                db.close();
                                listener.onFinishMatHangDialog(0);
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
        this.listener = (MatHangDialogListener) context;
    }
}
