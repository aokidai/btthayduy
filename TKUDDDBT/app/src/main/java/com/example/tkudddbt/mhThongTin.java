package com.example.tkudddbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class mhThongTin extends AppCompatActivity {
    Button btnThem, btnThoat;
    ListView lvTT;
    Spinner spQQ;
    int poss = -1;
    SQLiteDatabase db;
    ArrayList<pesion> dsQLTT = new ArrayList<pesion>();
    MyAdapterQLTT adapter;
    ArrayList<quequan> dsQueQuan = new ArrayList<quequan>();
    ArrayAdapter<quequan> adapter2;
    int posselected = -1;
    int posselected2 = -1;
    private void initWedget(){
        btnThem = (Button) findViewById(R.id.btnTTThem);
        btnThoat = (Button) findViewById(R.id.btnTTThoat);
        lvTT = (ListView) findViewById(R.id.lvTT);
        spQQ = (Spinner) findViewById(R.id.spTTQueQuan);
    }
    private void getDSQLTT(){
        try{
            dsQLTT.add(new pesion("Tên", "Số DT", "Địa chỉ"));
            db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
            //Cursor c = db.rawQuery("Select * from tblPersion where idPersion " = '" position "'",null);
            Cursor c = db.query("tblPersion", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsQLTT.add(new pesion(c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString()));
                c.moveToNext();
            }
            adapter = new MyAdapterQLTT(this, android.R.layout.simple_list_item_1, dsQLTT);
            lvTT.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
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
            adapter2 = new ArrayAdapter<quequan>(this, android.R.layout.simple_spinner_item, dsQueQuan);
            adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spQQ.setAdapter(adapter2);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void comfirmDelete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Xác nhận để xóa...");
        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setMessage("Bạn có chắc xóa?");
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
                String idNguoi = dsQLTT.get(posselected).getId_Nguoi();
                if(db.delete("tblKhachhang", "idKH=?", new String[]{idNguoi})!=-1){
                    dsQLTT.remove(posselected);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplication(), "Xóa khách hàng thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontext, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuedit:
                pesion ps = dsQLTT.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mhThongTin.this, EditThongTin.class);
                bundle.putSerializable("ps", (Serializable) ps);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, MainActivity.EDIT_QL);
                return true;
            case R.id.mnudelete:
                comfirmDelete();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MainActivity.OPEN_QL:
                if(resultCode == MainActivity.SAVE_QL){
                    Bundle bundle = data.getBundleExtra("data");
                    pesion ps = (pesion) bundle.getSerializable("ps");
                    dsQLTT.add(ps);
                    adapter.notifyDataSetChanged();
                }
                break;
            case MainActivity.EDIT_QL:
                if (requestCode == MainActivity.SAVE_QL){
                    Bundle bundle = data.getBundleExtra("data");
                    pesion ps = (pesion) bundle.getSerializable("ps");
                    dsQLTT.set(posselected, ps);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_thong_tin);
        initWedget();
        //getDSQLTT();
        getQueQuan();
        registerForContextMenu(lvTT);
        Intent callerIntent = getIntent();
        Bundle pack = callerIntent.getBundleExtra("bt");
        poss = pack.getInt("ds");
        spQQ.setSelection(poss);

        try{
            //dsQLTT.add(new pesion("Tên", "Số DT", "Địa chỉ"));
            db = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select * from tblPersion where idQue " + " = '" + poss +"'", null);
            //Cursor c = db.query("tblPersion", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()){
                dsQLTT.add(new pesion(c.getString(1).toString(), c.getString(2).toString(), c.getString(3).toString()));
                c.moveToNext();
            }
            adapter = new MyAdapterQLTT(getApplication(), android.R.layout.simple_list_item_1, dsQLTT);
            lvTT.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mhThongTin.this, InsertThongTin.class);
                startActivityForResult(intent, MainActivity.OPEN_QL);
            }
        });
        lvTT.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });


    }
}