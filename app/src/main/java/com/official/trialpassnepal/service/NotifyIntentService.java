package com.official.trialpassnepal.service;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.ReminderActivity;
import com.official.trialpassnepal.utils.CommonMethods;

import java.util.List;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotifyIntentService extends IntentService {
    private int mNotificationId;
    private String msg;
    public NotifyIntentService() {
        super("NotifyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mNotificationId = intent.getExtras().getInt("notification_id");
            msg = intent.getExtras().getString("msg");
//            if(isAppOnForeground(this)){
//                showSystemAlert(msg);
//            }else {
                System.out.println("Rabin is testing: Data in on create " + mNotificationId);
                NotificationCompat.Builder mBuilder;
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("TPN")
                        .setContentText(msg);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, ReminderActivity.class), 0);
                mBuilder.setContentIntent(contentIntent);
                mBuilder.setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true);

                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
//            }
        }
    }

    private void showSystemAlert(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setTitle("Reminder");
        /**ADD THIS FOR DISPLAY THE ALERT ANYWHERE*/
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }

    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
