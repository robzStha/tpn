package com.official.trialpassnepal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.official.trialpassnepal.objects.ReminderObject;
import com.official.trialpassnepal.utils.CommonMethods;

import java.util.ArrayList;

public class SyncReceiver extends BroadcastReceiver {

    private ArrayList<ReminderObject> reminderObjects;

    @Override
        public void onReceive(Context context, Intent intent) {
        System.out.println("Sync started at: "+System.currentTimeMillis());
        CommonMethods.syncData(context);
//            Toast.makeText(context, "Received by receiver", Toast.LENGTH_SHORT).show();

//            Intent service = new Intent(context, NotifyIntentService.class);
//            int notificationId = intent.getExtras().getInt("notification_id");
//            String msg = intent.getExtras().getString("msg");
//
//            if(notificationId==0){
//                msg = "Your have reminders. Please check.";
////                DbReminders dbReminders;
////                dbReminders = new DbReminders(context);
////                reminderObjects = dbReminders.getReminders();
////                ReminderActivity activity = new ReminderActivity();
////                activity.initReminder(reminderObjects, context);
//            }
//
//            service.putExtra("notification_id", notificationId);
//            service.putExtra("msg", msg);
//            System.out.println("Rabin is testing: notification id: " +notificationId);
//            // Start the service, keeping the device awake while it is launching.
//            context.startService(service);
        }
    }