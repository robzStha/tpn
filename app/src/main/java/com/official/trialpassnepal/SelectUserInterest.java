package com.official.trialpassnepal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.official.trialpassnepal.service.ReminderReceiver;
import com.official.trialpassnepal.service.SyncReceiver;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.CommonMethods;
import com.official.trialpassnepal.utils.Opener;
import com.official.trialpassnepal.utils.SharedPreference;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Calendar;

public class SelectUserInterest extends BaseActivity implements View.OnClickListener {

    ImageView ivCar, ivBike, ivBoth;
    SharedPreference pref;
    Opener open;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_user_interest;
    }

    @Override
    protected String actionBarTitle() {
        return "Trial Pass Nepal";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_user_interest);
        ivCar = (ImageView) findViewById(R.id.iv_car);
        ivBike = (ImageView) findViewById(R.id.iv_bike);
        ivBoth = (ImageView) findViewById(R.id.iv_both);

        open = new Opener(this);

        pref = new SharedPreference(getApplicationContext());
        ivCar.setOnClickListener(this);
        ivBike.setOnClickListener(this);
        ivBoth.setOnClickListener(this);
        hideMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_car:
                pref.setKeyValues(CommonDef.USER_INTEREST, CommonDef.CAR);
                break;
            case R.id.iv_bike:
                pref.setKeyValues(CommonDef.USER_INTEREST, CommonDef.BIKE);
                break;
            case R.id.iv_both:
                pref.setKeyValues(CommonDef.USER_INTEREST, CommonDef.BOTH);
                break;
        }
        Intent i = new Intent(SelectUserInterest.this, LandingActivity.class);
        startActivity(i);
        finish();
//        syncData();
//        Intent i = new Intent(SelectUserInterest.this, SyncReceiver.class);
//        i.putExtra("notification_id", CommonMethods.NOTIFICATION_ID);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(SelectUserInterest.this, CommonMethods.NOTIFICATION_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.SECOND, 10);
//        System.out.println("Rabin is testing: Remainder@ " + calendar.getTime() + " -- " + calendar.getTimeInMillis() + " -- " + System.currentTimeMillis());
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*10, pendingIntent);
    }
}

