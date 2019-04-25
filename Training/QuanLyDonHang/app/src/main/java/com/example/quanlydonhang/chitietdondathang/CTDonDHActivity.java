package com.example.quanlydonhang.chitietdondathang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;
import com.example.quanlydonhang.dondathang.DonDHActivity;

import java.util.ArrayList;

public class CTDonDHActivity extends AppCompatActivity implements CTDonDHDialogfragment.CTDonDHDialogListener, AdapterView.OnItemLongClickListener {

    private ImageButton imageButtonCloseCTDonDH;
    private Button btnInsertCT, buttonClearTextCT, btnEditCT;
    private EditText editTextSLDatCT;
    private ListView listViewCTDonDH;
    private ArrayList<CTDonDH> data;
    private CTDonDHAdapter adapter;
    public static CTDonDH ctDonDH;
    private int checkEditInsert = 0;
    private Spinner spinnerSoDDHCT, spinnerMaMatHang;

    private ArrayList<String> arrDonDH = new ArrayList<>();
    private ArrayList<String> arrMatHang = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctdon_dh);
        setControl();
        setEvent();
    }

    void setControl(){
        imageButtonCloseCTDonDH = findViewById(R.id.imageButtonCloseCTDonDH);
        btnInsertCT = findViewById(R.id.btnInsertCT);
        buttonClearTextCT = findViewById(R.id.buttonClearTextCT);
        btnEditCT = findViewById(R.id.btnEditCT);
        editTextSLDatCT = findViewById(R.id.editTextSLDatCT);

        listViewCTDonDH = findViewById(R.id.listViewCTDonDH);

        data = new ArrayList<>();
        adapter = new CTDonDHAdapter(this, data);
        loadDB();

        imageButtonCloseCTDonDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DatabaseHandler databaseHandler = new DatabaseHandler(CTDonDHActivity.this);
        databaseHandler.getListSoDDH(arrDonDH);
        spinnerSoDDHCT = findViewById(R.id.spinnerSoDDHCT);
        ArrayAdapter<String> adapterDDH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrDonDH);
        adapterDDH.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerSoDDHCT.setAdapter(adapterDDH);

        databaseHandler.getListMaMH(arrMatHang);
        spinnerMaMatHang = findViewById(R.id.spinnerMaMatHang);
        ArrayAdapter<String> adapterMH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrMatHang);
        adapterMH.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerMaMatHang.setAdapter(adapterMH);
    }

    @Override
    public void onFinishCTDonDHDialog(int input){
        if(input == 0){
            listViewCTDonDH.setEnabled(true);
            loadDB();
        }
        else if(input == 1){
            checkEditInsert = 1;
            int spinnerPosition = arrDonDH.indexOf(ctDonDH.getSODDH().toString());
            spinnerSoDDHCT.setSelection(spinnerPosition);
            spinnerSoDDHCT.setEnabled(false);

            int spinnerPositionMH = arrMatHang.indexOf(ctDonDH.getMAHG());
            spinnerMaMatHang.setSelection(spinnerPositionMH);
            spinnerMaMatHang.setEnabled(false);

            editTextSLDatCT.setText(ctDonDH.getSLDAT()+"");
            btnInsertCT.setVisibility(View.GONE);
            btnEditCT.setVisibility(View.VISIBLE);
        }
    }

    void setEvent(){
        listViewCTDonDH.setAdapter(adapter);
        listViewCTDonDH.setOnItemLongClickListener(this);

        buttonClearTextCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditInsert == 0){
                    spinnerSoDDHCT.setSelection(0);
                    spinnerMaMatHang.setSelection(0);
                }
                editTextSLDatCT.setText("");
            }
        });

        btnEditCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                updateDB();
                btnEditCT.setVisibility(View.GONE);
                btnInsertCT.setVisibility(View.VISIBLE);
                listViewCTDonDH.setEnabled(true);
                spinnerMaMatHang.setEnabled(true);
                spinnerSoDDHCT.setEnabled(true);
                loadDB();
                checkEditInsert = 0;
                Toast.makeText(CTDonDHActivity.this,"Update thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnInsertCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                DatabaseHandler databaseHandler = new DatabaseHandler(CTDonDHActivity.this);
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                String sql = "select * from CTDONDH where SODDH = " + Integer.parseInt(spinnerSoDDHCT.getSelectedItem().toString())
                        + " and MAHG ='" + spinnerMaMatHang.getSelectedItem().toString() +"'";
                Cursor curosr = db.rawQuery(sql, null);
                if(curosr.getCount() == 0 )
                {
                    saveDB();
                    loadDB();
                    Toast.makeText(CTDonDHActivity.this,"Insert thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CTDonDHActivity.this, "Chi tiết đơn đặt hàng này đã có!", Toast.LENGTH_SHORT).show();
                    spinnerSoDDHCT.requestFocus();
                }
                db.close();
            }
        });
    }
    public Boolean checkInput(){

        if(spinnerSoDDHCT.getSelectedItem().toString().equals("Chọn Số Đơn Đặt Hàng")){
            Toast.makeText(CTDonDHActivity.this, "Chưa chọn Số Đơn Đặt Hàng!",
                    Toast.LENGTH_SHORT).show();
            spinnerSoDDHCT.requestFocus();
            return false;
        }
        if(spinnerMaMatHang.getSelectedItem().toString().equals("Chọn Mã Mặt Hàng")){
            Toast.makeText(CTDonDHActivity.this, "Chưa chọn Mã Mặt Hàng!",
                    Toast.LENGTH_SHORT).show();
            spinnerMaMatHang.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextSLDatCT.getText().toString())){
            Toast.makeText(CTDonDHActivity.this, "Chưa nhập Số Lượng Đặt!",
                    Toast.LENGTH_SHORT).show();
            editTextSLDatCT.requestFocus();
            return false;
        }
        return true;
    }
    public void saveDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        CTDonDH ctDonDH = new CTDonDH();
        ctDonDH.setSODDH(Integer.parseInt(spinnerSoDDHCT.getSelectedItem().toString()));
        ctDonDH.setMAHG(spinnerMaMatHang.getSelectedItem().toString());
        ctDonDH.setSLDAT(Integer.parseInt(editTextSLDatCT.getText().toString()));
        db.saveCTDonDH(ctDonDH);
    }
    public void updateDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        CTDonDH ctDonDH = new CTDonDH();
        ctDonDH.setSODDH(Integer.parseInt(spinnerSoDDHCT.getSelectedItem().toString()));
        ctDonDH.setMAHG(spinnerMaMatHang.getSelectedItem().toString());
        ctDonDH.setSLDAT(Integer.parseInt(editTextSLDatCT.getText().toString()));
        db.updateCTDonDH(ctDonDH);
    }
    public void loadDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        db.getCTDonDHs(data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        ctDonDH = data.get(i);
        listViewCTDonDH.setEnabled(false);
        showCTDonDHDialog();
        return true;
    }
    private void showCTDonDHDialog() { // show fragment để Sửa tên báo thức
        CTDonDHDialogfragment ctDonDHDialogfragment = new CTDonDHDialogfragment();
        ctDonDHDialogfragment.show(getSupportFragmentManager(), "fragment_ct_ddh");
    }

}
