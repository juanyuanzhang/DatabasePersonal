package com.example.juan.textapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MDBHelper extends SQLiteOpenHelper{

    //Constructor
    public MDBHelper(Context context) {
        super(context,"membersource", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {  //建立資料表格時機_OnCreate
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS membersource"+
        "(_id INTEGER PRIMARY KEY autoincrement ,name,phone,email,birth)");    //正常SQL語法最後要分號，但是使用execSQL()方法所以不用加『 ; 』

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS membersource");
        onCreate(sqLiteDatabase);
    }
}
