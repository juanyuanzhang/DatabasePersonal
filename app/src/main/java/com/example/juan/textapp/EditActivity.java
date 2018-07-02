package com.example.juan.textapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    Button ok,back;
    TextView textViewAE;
    EditText tvname,tvphone,tvemail,tvbirth;
    MDBAdapter mdbAdapter;
    public String ename, ephone, eemail, ebirth;
    public Bundle mbData;
    public int index;
    private int mYear, mMonth, mDay;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ok = findViewById(R.id.ok);
        back = findViewById(R.id.back);
        textViewAE = findViewById(R.id.textViewAE);
        tvname = findViewById(R.id.nameAE);
        tvphone = findViewById(R.id.phoneAE);
        tvemail = findViewById(R.id.emailAE);
        tvbirth = findViewById(R.id.birthAE);
        mdbAdapter = new MDBAdapter(this);

        mbData = this.getIntent().getExtras();


        if (mbData.getString("type").equals("edit")){
            textViewAE.setText("編輯聯絡人");
            Cursor cursor = mdbAdapter.queryByName(mbData.getString("item_name"));
            index = cursor.getInt(0);
            tvname.setText(cursor.getString(1));
            tvphone.setText(cursor.getString(2));
            tvemail.setText(cursor.getString(3));
            tvbirth.setText(cursor.getString(4));
        }

        ok.setOnClickListener(this);
        back.setOnClickListener(this);
        tvbirth.setOnClickListener(this);

        }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.birthAE:

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay= c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(EditActivity.this, year + "-" + (month+1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();

                        tvbirth.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
                break;
            case R.id.ok:
                ename = tvname.getText().toString();
                ephone = tvphone.getText().toString();
                eemail = tvemail.getText().toString();
                ebirth = tvbirth.getText().toString();
                Log.i("new_name=",ename);
                Log.i("new_phone=",ephone);
                Log.i("new_mail=",eemail);
                mdbAdapter = new MDBAdapter(EditActivity.this);
                if(mbData.getString("type").equals("add")){
                        try{
                            mdbAdapter.createContacts(ename, ephone, eemail, ebirth);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            //回到列表
                            Intent i = new Intent(this, PersonalActivity.class);
                            startActivity(i);
                        }
                }else if(mbData.getString("type").equals("edit")){
                    try{
                        mdbAdapter.updateContacts(index,ename,ephone,eemail,ebirth);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent();
                        i.setClass(this,ShowActivity.class);
                        i.putExtra("type","edit");
                        i.putExtra("item_name",tvname.getText().toString());
                        startActivity(i);
                    }
                }
                break;
            case R.id.back:
                if(mbData.getString("type").equals("add")){
                    Intent i = new Intent(this, PersonalActivity.class);
                    startActivity(i);
                }else if(mbData.getString("type").equals("edit")){
                    Intent i = new Intent();
                    i.setClass(this,ShowActivity.class);
                    i.putExtra("type","edit");
                    i.putExtra("item_name",tvname.getText().toString());
                    startActivity(i);
                }
                break;
        }
    }
}
