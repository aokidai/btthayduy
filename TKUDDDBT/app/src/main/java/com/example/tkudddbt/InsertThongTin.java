package com.example.tkudddbt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class InsertThongTin extends Activity {
    Button btnThoat;
    EditText edtTen, edtSoDT, edtDiachi;
    Spinner spQueQuan;
    SQLiteDatabase db;
    ArrayList<quequan> dsQueQuan = new ArrayList<quequan>();
    ArrayAdapter<quequan> adapter;
    int posSpinner = -1;
    private void initWidget(){
        btnThoat = (Button) findViewById(R.id.btnInsertBack);
        edtTen = (EditText) findViewById(R.id.txtInsertTen);
        edtSoDT = (EditText) findViewById(R.id.txtInsertSoDT);
        edtDiachi = (EditText) findViewById(R.id.txtInsertDiaChi);
        spQueQuan = (Spinner) findViewById(R.id.spInsertQueQuan);
    }
    private void getQueQuan(){
        try {
            db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblQueQuan", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsQueQuan.add(new quequan(c.getInt(0)+"", c.getString(1).toString()));
                c.moveToNext();
            }
            adapter = new ArrayAdapter<quequan>(this, android.R.layout.simple_spinner_item, dsQueQuan);
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spQueQuan.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private long luuQLTT(){
        db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        try{
            values.put("idQue", dsQueQuan.get(posSpinner).getIdQue());
            values.put("Ten", edtTen.getText().toString());
            values.put("SoDT", edtSoDT.getText().toString());
            values.put("DiaChi", edtDiachi.getText().toString());
            return (db.insert("tblPersion", null, values));
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }
    private void xoaHoaDon(){
        edtTen.setText("");
        edtSoDT.setText("");
        edtDiachi.setText("");
        spQueQuan.setSelection(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_thong_tin);
        initWidget();
        getQueQuan();
        spQueQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = luuQLTT();
                if (id!=-1){
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    pesion ps = new pesion(edtTen.getText().toString(), edtSoDT.getText().toString(), edtDiachi.getText().toString(), dsQueQuan.get(posSpinner).getIdQue() );
                    bundle.putSerializable("ps", ps);
                    intent.putExtra("data", bundle);
                    setResult(MainActivity.SAVE_QL, intent);
                    Toast.makeText(getApplication(), "Thêm người thành công!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}