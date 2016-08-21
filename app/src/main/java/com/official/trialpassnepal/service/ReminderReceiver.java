package com.official.trialpassnepal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.official.trialpassnepal.ReminderActivity;
import com.official.trialpassnepal.db.DbReminders;
import com.official.trialpassnepal.objects.ReminderObject;

import java.util.ArrayList;

public class ReminderReceiver extends BroadcastReceiver {

    private ArrayList<ReminderObject> reminderObjects;

    @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "Received by receiver", Toast.LENGTH_SHORT).show();

            Intent service = new Intent(context, NotifyIntentService.class);
            int notificationId = intent.getExtras().getInt("notification_id");
            String msg = intent.getExtras().getString("msg");

            if(notificationId==0){
                msg = "Your have reminders. Please check.";
//                DbReminders dbReminders;
//                dbReminders = new DbReminders(context);
//                reminderObjects = dbReminders.getReminders();
//                ReminderActivity activity = new ReminderActivity();
//                activity.initReminder(reminderObjects, context);
            }

            service.putExtra("notification_id", notificationId);
            service.putExtra("msg", msg);
            System.out.println("Rabin is testing: notification id: " +notificationId);
            // Start the service, keeping the device awake while it is launching.
            context.startService(service);
        }
    }