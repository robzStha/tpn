package com.official.trialpassnepal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.official.trialpassnepal.objects.ReminderObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DbReminders {

    SQLiteDatabase db;
    DbHelper dbHelper;

    Context context;

    public DbReminders(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public void insertReminders(ArrayList<ReminderObject> reminderObjects) {
        truncate();
        open();
        db.beginTransaction();
        ContentValues values = new ContentValues();

        try {
            for (ReminderObject reminderObject : reminderObjects) {  // loop through your records
                values.put("title", reminderObject.getRemainderType());
                values.put("date", reminderObject.getDate());
                values.put("fullDate", convertCalendarToString(reminderObject.getFullDate()));
                values.put("notificationId", reminderObject.getNotificationId());
                db.insert(DbHelper.TBL_REMINDER, null, values);
                values.clear();
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        close();
    }

    public ArrayList<ReminderObject> getReminders() {
        String query = "Select * from " + DbHelper.TBL_REMINDER;
        open();
        Cursor cursor = db.rawQuery(query, null);
        return loadReminders(cursor);
    }

    private ArrayList<ReminderObject> loadReminders(Cursor cursor) {

        ArrayList<ReminderObject> reminderObjects = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String title, date, fullDate;
            long id;
            int notificationId;

            title = cursor.getString(cursor.getColumnIndex("title"));
            date = cursor.getString(cursor.getColumnIndex("date"));
            id = cursor.getLong(cursor.getColumnIndex("id"));
            fullDate = cursor.getString(cursor.getColumnIndex("fullDate"));
            notificationId = cursor.getInt(cursor.getColumnIndex("notificationId"));

            System.out.println("From db: " + title + " -- " + date + " -- " + id + " -- " + fullDate);

            ReminderObject categoryObject = new ReminderObject(title, date, id);
            categoryObject.setFullDate(convertStringToCalendar(fullDate));
            categoryObject.setNotificationId(notificationId);
            reminderObjects.add(categoryObject);
            cursor.moveToNext();
        }
        close();
        return reminderObjects;

    }

    private Calendar convertStringToCalendar(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
        try {
            cal.setTime(sdf.parse(date));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    private String convertCalendarToString(Calendar date) {
        return date.getTime().toString();
    }

    public void truncate() {
        open();
        db.delete(DbHelper.TBL_REMINDER, null, null);
        close();
    }

    public void delete(long id) {
        open();
        db.delete(DbHelper.TBL_REMINDER, "id=" + id, null);
        close();
    }
}
