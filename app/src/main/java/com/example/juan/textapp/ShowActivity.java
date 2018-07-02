package com.example.juan.textapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {
    private TextView tvname,tvphone,tvemail,tvbirth;
    public Bundle mbData;
    public MDBAdapter mdbAdapter;
    public int index;
    private String name;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvname = findViewById(R.id.nameshow);
        tvphone = findViewById(R.id.phoneshow);
        tvemail = findViewById(R.id.emailshow);
        tvbirth = findViewById(R.id.birthshow);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iback = new Intent(ShowActivity.this,PersonalActivity.class);
                startActivity(iback);
            }
        });

        mbData = this.getIntent().getExtras();
        name = mbData.getString("item_name");
        mdbAdapter = new MDBAdapter(this);
        Cursor cursor = mdbAdapter.queryByName(name);
        index = cursor.getInt(0);
        tvname.setText(cursor.getString(1));
        tvphone.setText(cursor.getString(2));
        tvemail.setText(cursor.getString(3));
        tvbirth.setText(cursor.getString(4));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("type","edit");
                intent.putExtra("item_name",name);
                intent.setClass(ShowActivity.this, EditActivity.class );
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.del_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog dialog =null;
        AlertDialog.Builder builder=null;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("重要訊息:")
                .setMessage("確定要刪除此筆資料嗎?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean isDeleted = mdbAdapter.deleteContacts(index);
                        if(isDeleted)
                            Toast.makeText(ShowActivity.this,"已刪除!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        return super.onOptionsItemSelected(item);

    }
}
