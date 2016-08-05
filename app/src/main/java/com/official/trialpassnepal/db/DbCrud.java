package com.official.trialpassnepal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbCrud {

    SQLiteDatabase db;
    DbHelper dbHelper;

    Context context;

    public DbCrud(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        open();
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }



}
