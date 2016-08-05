package com.official.trialpassnepal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import com.official.trialpassnepal.SMSResultActivity;

public class MySMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        SmsMessage shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

        Log.d("SMSReceiver", "SMS message sender: " +
                shortMessage.getOriginatingAddress());
        Log.d("SMSReceiver", "SMS message text: " +
                shortMessage.getDisplayMessageBody());

        // Start Application's  MainActivty activity
        if(shortMessage.getOriginatingAddress().equals("4321")) {
            Intent smsIntent = new Intent(context, SMSResultActivity.class);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            smsIntent.putExtra("sms_str", shortMessage.getDisplayMessageBody());
            context.startActivity(smsIntent);
        }

//      AlertDialog.Builder builder = new AlertDialog.Builder(context);
//      builder.setTitle(shortMessage.getOriginatingAddress());
//      builder.setMessage(shortMessage.getDisplayMessageBody());
//      builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialogInterface, int i) {
//              dialogInterface.dismiss();
//          }
//      });
//
//      AlertDialog dialog = builder.create();
//      dialog.show();

    }

}