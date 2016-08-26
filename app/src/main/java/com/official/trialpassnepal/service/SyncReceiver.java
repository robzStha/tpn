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
        }
    }