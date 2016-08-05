package com.official.trialpassnepal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.EditTextTypeFaced;


public class SendSmsActivity extends BaseActivity {

    EditTextTypeFaced etVehicleNo;
    ButtonTypeFaced btnCheckTax;
    String phoneNo = "4321";

    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_sms;
    }

    @Override
    protected String actionBarTitle() {
        return "Inquiry Vehicle Info";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        etVehicleNo = (EditTextTypeFaced) findViewById(R.id.et_vehicleNo);
        btnCheckTax = (ButtonTypeFaced) findViewById(R.id.btn_checkTax);

        btnCheckTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "VTAX " + etVehicleNo.getText().toString();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, msg, null, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SendSmsActivity.this);
                    builder.setTitle(getString(R.string.app_name));
                    builder.setMessage("Message has been sent. Please check you SMS inbox in a while");
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Toast.makeText(getApplicationContext(), "Message Sent",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),
                            ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });
    }
}