package com.example.dell.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2018/2/27.
 */

    public class MySql extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "user";
    public static  final String _ID = "_id";
    public static final String  CONTENT ="content";
    public MySql(Context context){
        super(context,"ucai.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
         db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
         _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
         CONTENT + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
