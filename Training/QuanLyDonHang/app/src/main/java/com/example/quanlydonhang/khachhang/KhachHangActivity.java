package com.example.quanlydonhang.khachhang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

public class KhachHangActivity extends AppCompatActivity implements KhachHangDialogFragment.KhachHangDialogListener, AdapterView.OnItemLongClickListener {
    private ImageButton imageButtonCloseKH;
    private Button btnEditKH, btnInsertKH, btnClearTextKH, btnFindKH, btnLoadKH;
    private EditText editTextMaKH, editTextTenKH;
    private ListView listViewKH;
    private ArrayList<KhachHang> data;
    private KhachHangAdapter adapter;
    public static KhachHang khachHang;
    private int checkEditInsert = 0;
    private DatabaseHandler databaseHandler;

    private ArrayList<String> arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        setControl();
        setEvent();
    }

    void setControl(){
        editTextMaKH = findViewById(R.id.editTextMaKH);
        editTextTenKH = findViewById(R.id.editTextTenKH);
        btnEditKH = findViewById(R.id.btnEditKH);
        btnInsertKH = findViewById(R.id.btnInsertKH);
        btnClearTextKH = findViewById(R.id.buttonClearTextKH);
        imageButtonCloseKH = findViewById(R.id.imageButtonCloseKH);
        btnFindKH = findViewById(R.id.btnFindKH);
        btnLoadKH = findViewById(R.id.btnLoadKH);

        listViewKH = findViewById(R.id.listViewKH);
        data = new ArrayList<>();
        adapter = new KhachHangAdapter(this, data);
        databaseHandler = new DatabaseHandler(KhachHangActivity.this);
        loadDB();


    }

    @Override
    public void onFinishKHDialog(int input){
        if(input == 0){
            loadDB();
            listViewKH.setEnabled(true);
        }
        else if(input == 1){
            checkEditInsert = 1;
            listViewKH.setEnabled(false);
            editTextMaKH.setText(khachHang.getMaKH());
            editTextMaKH.setEnabled(false);
            editTextTenKH.setText(khachHang.getTenKH());
            btnInsertKH.setVisibility(View.GONE);
            btnEditKH.setVisibility(View.VISIBLE);
            btnFindKH.setVisibility(View.GONE);
            btnLoadKH.setVisibility(View.GONE);
        }
        else if(input == 2){
            getTop3();
            listViewKH.setEnabled(true);
        }
    }

    void setEvent(){
        listViewKH.setAdapter(adapter);
        listViewKH.setOnItemLongClickListener(this);

        btnClearTextKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditInsert == 0){
                    editTextMaKH.setText("");
                }
                editTextTenKH.setText("");
            }
        });
        imageButtonCloseKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                updateDB();
                btnEditKH.setVisibility(View.GONE);
                btnInsertKH.setVisibility(View.VISIBLE);
                btnFindKH.setVisibility(View.VISIBLE);
                btnLoadKH.setVisibility(View.VISIBLE);
                listViewKH.setEnabled(true);
                editTextMaKH.setEnabled(true);
                loadDB();
                checkEditInsert = 0;
                Toast.makeText(KhachHangActivity.this,"Update thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnInsertKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                String sql = "select * from KHACHHANG where MAKH ='" + editTextMaKH.getText().toString().trim() +"'";
                Cursor curosr = db.rawQuery(sql, null);
                if(curosr.getCount() == 0 )
                {
                    saveDB();
                    loadDB();
                    Toast.makeText(KhachHangActivity.this,"Insert thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(KhachHangActivity.this, "Khách hàng này đã có!", Toast.LENGTH_SHORT).show();
                    editTextMaKH.requestFocus();
                }
                db.close();
            }
        });
        btnFindKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtUrl = new EditText(KhachHangActivity.this);
                new AlertDialog.Builder(KhachHangActivity.this)
                        .setTitle("Nhập Mã Khách Hàng:")
                        .setView(txtUrl)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String url = txtUrl.getText().toString();
                                DatabaseHandler db = new DatabaseHandler(KhachHangActivity.this);
                                if(data!=null) {
                                    data.clear();
                                }
                                db.findByMaKH(data, url);
                                if (data.size() == 0) {
                                    Toast.makeText(getApplication(), "Không tìm thấy khách hàng nào", Toast.LENGTH_SHORT).show();
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
        btnLoadKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDB();
            }
        });
    }
    public Boolean checkInput(){
        if(TextUtils.isEmpty(editTextMaKH.getText().toString())){
            Toast.makeText(KhachHangActivity.this, "Chưa nhập Mã Khách Hàng!", Toast.LENGTH_SHORT).show();
            editTextMaKH.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextTenKH.getText().toString())){
            Toast.makeText(KhachHangActivity.this, "Chưa nhập Tên Khách Hàng!",
                    Toast.LENGTH_SHORT).show();
            editTextTenKH.requestFocus();
            return false;
        }
        return true;
    }
    public void saveDB(){
        KhachHang khachHangSave = new KhachHang();

        khachHangSave.setMaKH(editTextMaKH.getText().toString());
        khachHangSave.setTenKH(editTextTenKH.getText().toString());
        databaseHandler.saveKhachHangs(khachHangSave);
    }
    public void updateDB(){
        KhachHang khachHangup = new KhachHang();

        khachHangup.setMaKH(editTextMaKH.getText().toString());
        khachHangup.setTenKH(editTextTenKH.getText().toString());

        databaseHandler.updateKhachHang(khachHangup);
    }
    public void loadDB(){
        data.clear();
        databaseHandler.getKhachHangs(data);
        adapter.notifyDataSetChanged();
    }
    public void getTop3(){
        data.clear();
        databaseHandler.getKhachHangMuaNhieuNhat(data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        khachHang = data.get(i);
        showDDHDialog();
        return true;
    }
    private void showDDHDialog() { // show fragment để Sửa tên báo thức
        KhachHangDialogFragment khachHangDialogFragment = new KhachHangDialogFragment();
        khachHangDialogFragment.show(getSupportFragmentManager(), "fragment_kh");
    }


}
