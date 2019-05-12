package com.example.quanlydonhang.dondathang;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlydonhang.DatabaseHandler;
import com.example.quanlydonhang.R;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonDHActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, DonDHDialogFragment.DDHDialogListener {

    private ImageButton imageButtonCloseDDH;
    private Button btnEdit, btnInsert, btnClearText, btnFindBySoDDH, btnReLoadDDH;
    private EditText editTextSoDDH, editTextSoNgay, editTextTinhTrang;
    private TextView textViewNgayDH;
    private ListView listViewDDH;
    private ArrayList<DonDH> data;
    private DonDHAdapter adapter;
    private String myFormat = "dd-MM-yyyy HH:mm:ss:SSS";
    final Calendar myCalendar = Calendar.getInstance();
    public static DonDH donDH;
    private int checkEditInsert = 0;
    private Spinner spinMaKH;
    private DatabaseHandler databaseHandler;

    private ArrayList<String> arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dh);
        setControl();
        setEvent();
    }
    void setControl(){
        editTextSoDDH = findViewById(R.id.editTextSoDDH);
        //editTextMaKH = findViewById(R.id.editTextMaKH);
        textViewNgayDH = findViewById(R.id.textViewNgayDH);
        editTextSoNgay = findViewById(R.id.editTextSoNgay);
        editTextTinhTrang = findViewById(R.id.editTextTinhTrang);
        btnEdit = findViewById(R.id.btnEdit);
        btnInsert = findViewById(R.id.btnInsert);
        btnClearText = findViewById(R.id.buttonClearText);
        imageButtonCloseDDH = findViewById(R.id.imageButtonCloseDDH);
        btnFindBySoDDH = findViewById(R.id.btnFindBySoDDH);
        btnReLoadDDH = findViewById(R.id.btnReLoadDDH);

        listViewDDH = findViewById(R.id.listViewDDH);
        data = new ArrayList<>();
        adapter = new DonDHAdapter(this, data);
        databaseHandler = new DatabaseHandler(DonDHActivity.this);
        loadDB();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        textViewNgayDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DonDHActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageButtonCloseDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        databaseHandler.getMaKH(arr);
        spinMaKH = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinMaKH.setAdapter(adapter);
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textViewNgayDH.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public void onFinishDDHHDialog(int input){
        if(input == 0){
            loadDB();
            listViewDDH.setEnabled(true);
        }
        else if(input == 1){
            listViewDDH.setEnabled(false);
            checkEditInsert = 1;
            editTextSoDDH.setText(donDH.getSoDH()+"");
            editTextSoDDH.setEnabled(false);
            int spinnerPosition = arr.indexOf(donDH.getMaKH());
            spinMaKH.setSelection(spinnerPosition);
            textViewNgayDH.setText(donDH.getStringNgayDH());
            editTextSoNgay.setText(donDH.getSoNgay()+"");
            editTextTinhTrang.setText(donDH.getTinhTrang());
            btnInsert.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
            btnFindBySoDDH.setVisibility(View.GONE);
            btnReLoadDDH.setVisibility(View.GONE);
            //loadOneDDH();
        }
    }

    void setEvent(){
        listViewDDH.setAdapter(adapter);
        listViewDDH.setOnItemLongClickListener(this);

        btnClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditInsert == 0){
                    editTextSoDDH.setText("");
                }
                //editTextMaKH.setText("");
                textViewNgayDH.setText("");
                editTextSoNgay.setText("");
                editTextTinhTrang.setText("");
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                updateDB();
                btnEdit.setVisibility(View.GONE);
                btnInsert.setVisibility(View.VISIBLE);
                btnFindBySoDDH.setVisibility(View.VISIBLE);
                btnReLoadDDH.setVisibility(View.VISIBLE);
                listViewDDH.setEnabled(true);
                editTextSoDDH.setEnabled(true);
                loadDB();
                checkEditInsert = 0;
                Toast.makeText(DonDHActivity.this,"Update thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                DatabaseHandler databaseHandler = new DatabaseHandler(DonDHActivity.this);
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                String sql = "select * from DONDH where SODDH = " + Integer.parseInt(editTextSoDDH.getText().toString());
                Cursor curosr = db.rawQuery(sql, null);
                if(curosr.getCount() == 0 )
                {
                    saveDB();
                    loadDB();
                    Toast.makeText(DonDHActivity.this,"Insert thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DonDHActivity.this, "Đơn đặt hàng này đã có!", Toast.LENGTH_SHORT).show();
                    editTextSoDDH.requestFocus();
                }
                db.close();
            }
        });
        btnReLoadDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDB();
            }
        });
        btnFindBySoDDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtUrl = new EditText(DonDHActivity.this);
                txtUrl.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                new AlertDialog.Builder(DonDHActivity.this)
                        .setTitle("Nhập Số Đơn Hàng:")
                        .setView(txtUrl)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int url = Integer.parseInt(txtUrl.getText().toString());
                                DatabaseHandler db = new DatabaseHandler(DonDHActivity.this);
                                if(data!=null) {
                                    data.clear();
                                }
                                db.findBySoDDH(data, url);
                                if (data.size() == 0) {
                                    Toast.makeText(getApplication(), "Không tìm thấy đơn đặt hàng nào", Toast.LENGTH_SHORT).show();
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
        if(TextUtils.isEmpty(editTextSoDDH.getText().toString())){
            Toast.makeText(DonDHActivity.this, "Chưa nhập Số Đơn Đặt Hàng!", Toast.LENGTH_SHORT).show();
            editTextSoDDH.requestFocus();
            return false;
        }
        if(spinMaKH.getSelectedItem().toString().equals("Chọn Mã Khách Hàng")){
            Toast.makeText(DonDHActivity.this, "Chưa chọn Mã Khách Hàng!",
                    Toast.LENGTH_SHORT).show();
            spinMaKH.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(textViewNgayDH.getText().toString())){
            Toast.makeText(DonDHActivity.this, "Chưa chọn Ngày Đặt Hàng!",
                    Toast.LENGTH_SHORT).show();
            textViewNgayDH.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(editTextSoNgay.getText().toString())){
            Toast.makeText(DonDHActivity.this, "Chưa nhập Số Ngày!",
                    Toast.LENGTH_SHORT).show();
            editTextSoNgay.requestFocus();
            return false;
        }
        if(!TextUtils.isDigitsOnly(editTextSoDDH.getText())){
            Toast.makeText(DonDHActivity.this, "Số đơn đặt hàng, chỉ nhập số!",
                    Toast.LENGTH_SHORT).show();
            editTextSoDDH.requestFocus();
            return false;
        }
        if(!Calendar.getInstance().getTime().after(myCalendar.getTime())){
            Toast.makeText(DonDHActivity.this, "Ngày đặt hàng không được lớn hơn ngày hiện tại!",
                    Toast.LENGTH_SHORT).show();
            textViewNgayDH.requestFocus();
            return false;
        }
        return true;
    }
    public void saveDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        DonDH donDH = new DonDH();

        donDH.setSoDH(Integer.parseInt(editTextSoDDH.getText().toString()));
        donDH.setMaKH(spinMaKH.getSelectedItem().toString());

        donDH.setNgayDH(textViewNgayDH.getText().toString());
        donDH.setSoNgay(Integer.parseInt(editTextSoNgay.getText().toString()));
        donDH.setTinhTrang(editTextTinhTrang.getText().toString());
        db.saveDonDHs(donDH);
    }
    public void updateDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        DonDH donDH = new DonDH();

        donDH.setSoDH(Integer.parseInt(editTextSoDDH.getText().toString()));
        donDH.setMaKH(spinMaKH.getSelectedItem().toString());

        donDH.setNgayDH(textViewNgayDH.getText().toString());
        donDH.setSoNgay(Integer.parseInt(editTextSoNgay.getText().toString()));
        donDH.setTinhTrang(editTextTinhTrang.getText().toString());
        db.updateDonDH(donDH);
    }
    public void loadDB(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        db.getDonDHS(data);
        adapter.notifyDataSetChanged();
    }
    public void loadOneDDH(){
        DatabaseHandler db = new DatabaseHandler(this);
        data.clear();
        data.add(donDH);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        donDH = data.get(i);
        showDDHDialog();
        return false;
    }
    private void showDDHDialog() { // show fragment để Sửa tên báo thức
        DonDHDialogFragment donDHDialogFragment = new DonDHDialogFragment();
        donDHDialogFragment.show(getSupportFragmentManager(), "fragment_ddh");
    }


}
