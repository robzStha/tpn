package com.official.trialpassnepal.objects;

import java.util.Calendar;

/**
 * Created by SlowhandJr. on 8/20/16.
 */
public class ReminderObject {

    String remainderType, date;
    long id;
    Calendar fullDate;
    private int notificationId;

    public ReminderObject(String remainderType, String date, long id) {
        this.remainderType = remainderType;
        this.date = date;
        this.id = id;
    }

    public Calendar getFullDate() {
        return fullDate;
    }

    public void setFullDate(Calendar fullDate) {
        this.fullDate = fullDate;
    }

    public void setRemainderType(String remainderType) {
        this.remainderType = remainderType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemainderType() {
        return remainderType;
    }

    public String getDate() {
        return date;
    }

    public long getId(){
        return id;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
