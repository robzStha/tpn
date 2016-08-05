package com.official.trialpassnepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.official.trialpassnepal.objects.Category;
import com.official.trialpassnepal.objects.CategoryObject;

import java.util.ArrayList;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbCategories {

    SQLiteDatabase db;
    DbHelper dbHelper;

    Context context;

    public DbCategories(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public ArrayList<CategoryObject> getCategories(String offset, String limit) {
        String query = "Select * from tpn_category where published=1 limit " + offset + "," + limit;
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadCategories(cursor);
    }

    public void insertCategories(ArrayList<Category> alCategory){
        open();
        db.beginTransaction();
        ContentValues values = new ContentValues();

        try {
            for (Category category : alCategory) {  // loop through your records
                values.put("catId", category.getCatid());
                values.put("categoryName", category.getCategoryName());
                values.put("categoryDescription", category.getCategoryDescription());
                values.put("published", category.getPublished());
                db.insert("tpn_category", null, values);
                values.clear();
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        close();
    }

    public ArrayList<CategoryObject> getCategories() {
        String query = "Select * from tpn_category";
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadCategories(cursor);
    }

    private ArrayList<CategoryObject> loadCategories(Cursor cursor) {

        ArrayList<CategoryObject> alCatObj = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CategoryObject categoryObject = new CategoryObject();
            categoryObject.catId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("catid")));
            categoryObject.catName = cursor.getString(cursor.getColumnIndex("categoryName"));
            categoryObject.catDesc = cursor.getString(cursor.getColumnIndex("categoryDescription"));
            System.out.println("Cat id: " + categoryObject.catId + " cat Name: " + categoryObject.catName + " cat Desc: " + categoryObject.catDesc);
            alCatObj.add(categoryObject);
            cursor.moveToNext();
        }
        close();
        return alCatObj;

    }

    public void truncate(){
        open();
        db.delete("tpn_category", null, null);
        close();
    }

}
