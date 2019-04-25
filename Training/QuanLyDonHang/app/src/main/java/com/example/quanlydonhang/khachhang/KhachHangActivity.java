package com.example.quanlydonhang.khachhang;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;

import java.util.ArrayList;

public class KhachHangActivity extends AppCompatActivity implements KhachHangDialogFragment.KhachHangDialogListener, AdapterView.OnItemLongClickListener {
    private ImageButton imageButtonCloseKH;
    private Button btnEditKH, btnInsertKH, btnClearTextKH;
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

        listViewKH = findViewById(R.id.listViewKH);
        data = new ArrayList<>();
        adapter = new KhachHangAdapter(this, data);
        databaseHandler = new DatabaseHandler(KhachHangActivity.this);
        loadDB();

        imageButtonCloseKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onFinishKHDialog(int input){
        if(input == 0){
            loadDB();
            listViewKH.setEnabled(true);
        }
        else if(input == 1){
            checkEditInsert = 1;
            editTextMaKH.setText(khachHang.getMaKH());
            editTextMaKH.setEnabled(false);
            editTextTenKH.setText(khachHang.getTenKH());
            btnInsertKH.setVisibility(View.GONE);
            btnEditKH.setVisibility(View.VISIBLE);
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

        btnEditKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                updateDB();
                btnEditKH.setVisibility(View.GONE);
                btnInsertKH.setVisibility(View.VISIBLE);
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
                DatabaseHandler databaseHandler = new DatabaseHandler(KhachHangActivity.this);
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
        DatabaseHandler db = new DatabaseHandler(this);
        KhachHang khachHangSave = new KhachHang();

        khachHangSave.setMaKH(editTextMaKH.getText().toString());
        khachHangSave.setTenKH(editTextTenKH.getText().toString());
        db.saveKhachHangs(khachHangSave);
    }
    public void updateDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        KhachHang khachHangup = new KhachHang();

        khachHangup.setMaKH(editTextMaKH.getText().toString());
        khachHangup.setTenKH(editTextTenKH.getText().toString());

        db.updateKhachHang(khachHangup);
    }
    public void loadDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        db.getKhachHangs(data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        khachHang = data.get(i);
        listViewKH.setEnabled(false);
        showDDHDialog();
        return true;
    }
    private void showDDHDialog() { // show fragment để Sửa tên báo thức
        KhachHangDialogFragment khachHangDialogFragment = new KhachHangDialogFragment();
        khachHangDialogFragment.show(getSupportFragmentManager(), "fragment_kh");
    }


}
