package com.example.juan.textapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by juan on 2018/7/1.
 */
//自訂一個Adapter 來存放SQL方法: open() [呼叫helper] 、close()、......等
public class MDBAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_BIRTH = "birth";
    private MDBHelper mdbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private static final String TABLE_NAME = "membersource";
    private Intent i;
    private ContentValues values;

    public MDBAdapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }

    public void open() {
        mdbHelper = new MDBHelper(mCtx);
        mdb = mdbHelper.getWritableDatabase();
    }

    public void close() {
        if (mdbHelper != null)
            mdbHelper.close();
    }

    public Cursor listContacts() { //產生Cursor物件 裝入查詢資料 要給listView用
        Cursor mCursor = mdb.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_BIRTH}, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();  //移到第一筆資料(cursor 初始值為-1)
        }
        return mCursor;
    }
    //寫出四個方法:查詢 新增 修改 刪除
    public long createContacts(String name, String phone, String email, String birth) { //新增資料用到ContentValues
        try {
            values = new ContentValues();
            values.put(KEY_NAME, name);  //將資料放入 values
            values.put(KEY_PHONE, phone);
            values.put(KEY_EMAIL, email);
            values.put(KEY_BIRTH, birth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(mCtx, "新增成功", Toast.LENGTH_LONG).show();
        }
        return mdb.insert(TABLE_NAME, null, values);  //將values中的資料加入資料庫
    }
    public long updateContacts (int id,String name,String phone , String email, String birth) {//更新資料
        values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_BIRTH, birth);
        Toast.makeText(mCtx, "更新成功", Toast.LENGTH_LONG).show();
        return mdb.update(TABLE_NAME, values, "_id=" + id, null);
    }
    public boolean deleteContacts (int id){  //delete
        String[] args = {Integer.toString(id)};
        mdb.delete(TABLE_NAME,"_id=?",args);
        Toast.makeText(mCtx, "刪除成功", Toast.LENGTH_LONG).show();
        return true;
    }
    public Cursor queryByName(String item_name){//查詢資料會用到query()+Cursor
        Cursor mCursor = null ;
        mCursor=mdb.query(true,TABLE_NAME,new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_BIRTH},
                KEY_NAME + " LIKE '%" + item_name + "%'", null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
}
