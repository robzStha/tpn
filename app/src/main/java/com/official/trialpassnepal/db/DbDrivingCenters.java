package com.official.trialpassnepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.official.trialpassnepal.objects.CategoryObject;
import com.official.trialpassnepal.objects.DrivingCenter;
import com.official.trialpassnepal.objects.DrivingCenterObject;
import com.official.trialpassnepal.objects.QuestionImage;

import java.util.ArrayList;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbDrivingCenters {

    SQLiteDatabase db;
    DbHelper dbHelper;

    Context context;

    public DbDrivingCenters(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public ArrayList<DrivingCenterObject> getNearestDrivingCenters(Location myLoc, float distance) {
        String query = "Select * from tpn_driving_centers where (("+myLoc.getLatitude() + "- latitude)*("+myLoc.getLatitude()+" - latitude) + ("+myLoc.getLongitude()+" - longitude)*("+myLoc.getLongitude()+" - longitude)) <="+distance;
        System.out.println(query);
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadDrivingCenters(cursor);
    }

    private ArrayList<DrivingCenterObject> loadDrivingCenters(Cursor cursor) {

        ArrayList<DrivingCenterObject> alDrivingCenterObj = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DrivingCenterObject drivingCenterObject = new DrivingCenterObject();
            drivingCenterObject.setDcid(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dcid"))));
            drivingCenterObject.setDcConactNo(cursor.getString(cursor.getColumnIndex("dcContactno")));
            drivingCenterObject.setDcName(cursor.getString(cursor.getColumnIndex("dcName")));
            drivingCenterObject.setDcProprietor(cursor.getString(cursor.getColumnIndex("dcProprietor")));
            drivingCenterObject.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude"))));
            drivingCenterObject.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex("longitude"))));
//            System.out.println("Cat id: " + drivingCenterObject.catId + " cat Name: " + drivingCenterObject.catName + " cat Desc: " + drivingCenterObject.catDesc);
            alDrivingCenterObj.add(drivingCenterObject);
            cursor.moveToNext();
        }
        close();
        return alDrivingCenterObj;

    }


    public void truncate() {
        open();
        db.delete("tpn_driving_centers", null, null);
        close();
    }

    public void insertDrivingCenters(ArrayList<DrivingCenter> drivingCenters) {
        open();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            for (DrivingCenter drivingCenter : drivingCenters) {
                contentValues.put("dcid", drivingCenter.getDcid());
                contentValues.put("dcName", drivingCenter.getDcName());
                contentValues.put("dcProprietor", drivingCenter.getDcProprietor());
                contentValues.put("dcContactno", drivingCenter.getDcContactno());
                contentValues.put("latitude", drivingCenter.getLatitude());
                contentValues.put("longitude", drivingCenter.getLongitude());
                contentValues.put("published", drivingCenter.getPublished());
                db.insert("tpn_driving_centers", null, contentValues);
                contentValues.clear();
                System.out.println("Driving Center: " + drivingCenter.getDcName() + " - qid: " + drivingCenter.getDcid());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        close();
    }
}
