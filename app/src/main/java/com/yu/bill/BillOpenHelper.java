package com.yu.bill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BillOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bill.db";  //数据库名
    private static final int DB_VERSION = 1;    //数据库版本号
    private static final String TAG = "BillDBHelper";
    private static String path = "/data/data/com.yu.bill/databases/"+DB_NAME;//数据库路径
    public BillOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           String createTableSql = "CREATE TABLE IF NOT EXISTS " + TableDefine.TABLE_BILL
                   + " ( "
                   + TableDefine.BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + TableDefine.BILL_NAME + " TEXT, "
                   + TableDefine.BILL_PRICE + " TEXT, "
                   + TableDefine.BILL_TIME + " TEXT, "
                   + TableDefine.BILL_TYPE + " TEXT "
                   + ")";
           db.execSQL(createTableSql);
           Log.e(TAG,"数据库创建成功"+createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
