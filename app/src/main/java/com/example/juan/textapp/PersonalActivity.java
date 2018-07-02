package com.example.juan.textapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalActivity extends AppCompatActivity {
    private MDBAdapter mdbAdapter;
    private TextView textView;
    private ListView listView;
    private SimpleCursorAdapter dataAdapter;
    private Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);
        mdbAdapter = new MDBAdapter(this);
        if(mdbAdapter.listContacts().getCount()==0){
            listView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor item_cursor = (Cursor) listView.getItemAtPosition(i);
                String item_name = item_cursor.getString(item_cursor.getColumnIndexOrThrow("name"));
                Log.i("item_name=",item_name);
                Intent intent = new Intent();
                intent.putExtra("type","edit");
                intent.putExtra("item_name",item_name);
                intent.setClass(PersonalActivity.this, ShowActivity.class );
                startActivity(intent);
                finish();
            }
        });
        displaylistView();
    }
    private void displaylistView(){    //顯示資料庫的資料
        Cursor cursor = mdbAdapter.listContacts();
        String[] columns = new String[]{
                mdbAdapter.KEY_NAME,
                mdbAdapter.KEY_PHONE,
                mdbAdapter.KEY_EMAIL
        };
        int[] to = new int[]{
                R.id.name,//list_item
                R.id.phone,
                R.id.email
        };
        // 自訂的Adapter 需要使用SimpleCursorAdapter   來取資料放入listView中
        dataAdapter = new SimpleCursorAdapter(this,R.layout.list_item, cursor, columns, to, 0);
        listView.setAdapter(dataAdapter);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdbAdapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_bar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent = new Intent(this,EditActivity.class);
        intent.putExtra("type","add");
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
