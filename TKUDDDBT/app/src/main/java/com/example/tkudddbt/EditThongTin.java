package com.example.tkudddbt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditThongTin extends Activity {
    Button btnThoat;
    EditText edtTen, edtSoDT, edtDiachi;
    Spinner spQueQuan;
    SQLiteDatabase db;
    String idPersion;
    ArrayList<quequan> dsQueQuan = new ArrayList<quequan>();
    ArrayAdapter<quequan> adapter;
    int posSpinner = -1;
    private void initWidget(){
        btnThoat = (Button) findViewById(R.id.btnEditBack);
        edtTen = (EditText) findViewById(R.id.txtEditTen);
        edtSoDT = (EditText) findViewById(R.id.txtEditSoDT);
        edtDiachi = (EditText) findViewById(R.id.txtEditDiaChi);
        spQueQuan = (Spinner) findViewById(R.id.spEditQueQuan);
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        pesion ps = (pesion) bundle.getSerializable("ps");
        edtTen.setText(ps.getTen());
        edtSoDT.setText(ps.getSoDT());
        edtDiachi.setText(ps.getDiaChi());
        idPersion = ps.getId_Nguoi();
        int i=0;
        while (i<dsQueQuan.size()){
            if(ps.getId_Nguoi().contains(dsQueQuan.get(i).getIdQue()))
                break;
            i++;
        }
        spQueQuan.setSelection(i);
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
    private boolean luuQLTT(){
        db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        try {
            values.put("idQue", dsQueQuan.get(posSpinner).getIdQue());
            values.put("Ten", edtTen.getText().toString());
            values.put("SoDT", edtSoDT.getText().toString());
            values.put("DiaChi", edtDiachi.getText().toString());
            if(db.update("tblPersion", values, "idPersion=?", new String[]{idPersion})!=-1)
                return true;
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi: "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thong_tin);
        initWidget();
        getData();
        getQueQuan();
        btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (luuQLTT()){
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        pesion ps = new pesion(edtTen.getText().toString(), edtSoDT.getText().toString(), edtDiachi.getText().toString(),dsQueQuan.get(posSpinner).getIdQue());
                        bundle.putSerializable("ps", ps);
                        intent.putExtra("data", bundle);
                        setResult(MainActivity.SAVE_QL, intent);
                        Toast.makeText(getApplication(), "Thêm hóa đơn thành công!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        );
    }
}