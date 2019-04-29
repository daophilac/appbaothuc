package com.example.quanlydonhang.mathang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;

import java.util.ArrayList;

public class MatHangActivity extends AppCompatActivity implements MatHangDialogFragment.MatHangDialogListener, AdapterView.OnItemLongClickListener {
    private ImageButton imageButtonCloseMH;
    private Button btnInsertMH, buttonClearTextMH, btnEditMH, btnFindByMaHG, btnReLoadMH;
    private EditText editTextMaMatHang, editTextTenMH, editTextDacDiem, editTextDVT, editTextDonGia;
    private ListView listViewMatHang;
    private ArrayList<MatHang> data;
    private MatHangAdapter adapter;
    public static MatHang matHang;
    private int checkEditInsert = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang);
        setControl();
        setEvent();

    }
    void setControl(){
        editTextMaMatHang = findViewById(R.id.editTextMaMatHang);
        editTextTenMH = findViewById(R.id.editTextTenMH);
        editTextDacDiem = findViewById(R.id.editTextDacDiem);
        editTextDVT = findViewById(R.id.editTextDVT);
        editTextDonGia = findViewById(R.id.editTextDonGia);
        btnInsertMH = findViewById(R.id.btnInsertMH);
        buttonClearTextMH = findViewById(R.id.buttonClearTextMH);
        btnEditMH = findViewById(R.id.btnEditMH);
        imageButtonCloseMH = findViewById(R.id.imageButtonCloseMH);
        btnFindByMaHG = findViewById(R.id.btnFindByMaHG);
        btnReLoadMH = findViewById(R.id.btnReLoadMH);

        listViewMatHang = findViewById(R.id.listViewMatHang);
        data = new ArrayList<>();
        adapter = new MatHangAdapter(this, data);
        loadDB();

    }

    @Override
    public void onFinishMatHangDialog(int input){
        if(input == 0){
            loadDB();
            listViewMatHang.setEnabled(true);
        }
        else if(input == 1){
            checkEditInsert = 1;
            listViewMatHang.setEnabled(false);
            editTextMaMatHang.setText(matHang.getMAHG());
            editTextMaMatHang.setEnabled(false);
            editTextTenMH.setText(matHang.getTENHG());
            editTextDacDiem.setText(matHang.getDACDIEM());
            editTextDVT.setText(matHang.getDVT());
            editTextDonGia.setText(matHang.getDONGIA()+"");
            btnInsertMH.setVisibility(View.GONE);
            btnEditMH.setVisibility(View.VISIBLE);
            btnFindByMaHG.setVisibility(View.GONE);
            btnReLoadMH.setVisibility(View.GONE);
        }
        else if(input == 2){
            getTop3();
            listViewMatHang.setEnabled(true);
        }
    }

    void setEvent(){
        listViewMatHang.setAdapter(adapter);
        listViewMatHang.setOnItemLongClickListener(this);

        buttonClearTextMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditInsert == 0){
                    editTextMaMatHang.setText("");
                }
                editTextTenMH.setText("");
                editTextDacDiem.setText("");
                editTextDVT.setText("");
                editTextDonGia.setText("");
            }
        });

        btnEditMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                updateDB();
                btnEditMH.setVisibility(View.GONE);
                btnInsertMH.setVisibility(View.VISIBLE);
                btnFindByMaHG.setVisibility(View.VISIBLE);
                btnReLoadMH.setVisibility(View.VISIBLE);
                listViewMatHang.setEnabled(true);
                editTextMaMatHang.setEnabled(true);
                loadDB();
                checkEditInsert = 0;
                Toast.makeText(MatHangActivity.this,"Update thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnInsertMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                DatabaseHandler databaseHandler = new DatabaseHandler(MatHangActivity.this);
                if(databaseHandler.getMaMatHang(editTextMaMatHang.getText().toString().trim()))
                {
                    saveDB();
                    loadDB();
                    Toast.makeText(MatHangActivity.this,"Insert thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MatHangActivity.this, "Mặt hàng này đã có!", Toast.LENGTH_SHORT).show();
                    editTextMaMatHang.requestFocus();
                }
                databaseHandler.close();
            }
        });

        imageButtonCloseMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReLoadMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDB();
            }
        });
        btnFindByMaHG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtUrl = new EditText(MatHangActivity.this);
                new AlertDialog.Builder(MatHangActivity.this)
                        .setTitle("Nhập Mã Mặt Hàng:")
                        .setView(txtUrl)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String url = txtUrl.getText().toString();
                                DatabaseHandler db = new DatabaseHandler(MatHangActivity.this);
                                if(data!=null) {
                                    data.clear();
                                }
                                db.findByMaHG(data, url);
                                if (data.size() == 0) {
                                    Toast.makeText(getApplication(), "Không tìm thấy mặt hàng nào", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).show();
            }
        });
    }
    public Boolean checkInput(){
        if(TextUtils.isEmpty(editTextMaMatHang.getText().toString())){
            Toast.makeText(MatHangActivity.this, "Chưa nhập Mã Mặt Hàng!", Toast.LENGTH_SHORT).show();
            editTextMaMatHang.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextTenMH.getText().toString())){
            Toast.makeText(MatHangActivity.this, "Chưa nhập Tên Mặt Hàng!",
                    Toast.LENGTH_SHORT).show();
            editTextTenMH.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextDVT.getText().toString())){
            Toast.makeText(MatHangActivity.this, "Chưa nhập Đơn Vị Tính!",
                    Toast.LENGTH_SHORT).show();
            editTextDVT.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextDonGia.getText().toString())){
            Toast.makeText(MatHangActivity.this, "Chưa nhập Đơn Giá!",
                    Toast.LENGTH_SHORT).show();
            editTextDonGia.requestFocus();
            return false;
        }
        return true;
    }
    public void saveDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        MatHang matHangSave = new MatHang();

        matHangSave.setMAHG(editTextMaMatHang.getText().toString());
        matHangSave.setTENHG(editTextTenMH.getText().toString());
        matHangSave.setDACDIEM(editTextDacDiem.getText().toString());
        matHangSave.setDVT(editTextDVT.getText().toString());
        matHangSave.setDONGIA(Integer.parseInt(editTextDonGia.getText().toString()));
        db.saveMatHang(matHangSave);
    }
    public void updateDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        MatHang matHangup = new MatHang();

        matHangup.setMAHG(editTextMaMatHang.getText().toString());
        matHangup.setTENHG(editTextTenMH.getText().toString());
        matHangup.setDACDIEM(editTextDacDiem.getText().toString());
        matHangup.setDVT(editTextDVT.getText().toString());
        matHangup.setDONGIA(Integer.parseInt(editTextDonGia.getText().toString()));
        db.updateMatHang(matHangup);
    }
    public void loadDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        db.getMatHangs(data);
        adapter.notifyDataSetChanged();
    }
    public void getTop3(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        db.getMatHangBanNhieuNhat(data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        matHang = data.get(i);
        showDDHDialog();
        return true;
    }
    private void showDDHDialog() { // show fragment để Sửa tên báo thức
        MatHangDialogFragment matHangDialogFragment = new MatHangDialogFragment();
        matHangDialogFragment.show(getSupportFragmentManager(), "fragment_mh");
    }
}
