package com.example.user.ownread.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2016/7/28.
 */
public class OwnReadDB extends SQLiteOpenHelper {
    public String ID = "id";
    // private String
    public String mDBTable;

    public OwnReadDB(Context context) {
        super(context, "ownread.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
