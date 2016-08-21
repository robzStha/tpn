package com.official.trialpassnepal.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.official.trialpassnepal.CourseMaterial;
import com.official.trialpassnepal.LandingActivity;
import com.official.trialpassnepal.NearByDrivingCenters;
import com.official.trialpassnepal.ObjQstnAns;
import com.official.trialpassnepal.Registration;
import com.official.trialpassnepal.ReminderActivity;
import com.official.trialpassnepal.SendSmsActivity;
import com.official.trialpassnepal.Signs;
import com.official.trialpassnepal.SubQstnAns;
import com.official.trialpassnepal.TestListActivity;
import com.official.trialpassnepal.UsefulTipsActivity;
import com.official.trialpassnepal.WebViewActivity;
import com.official.trialpassnepal.view.MockTestViewPager;

/**
 * Created by Rabin
 *
 * This class is the activity opener
 */
public class Opener {
    Activity activity;
    Intent intent;

    public Opener(Activity activity) {
        this.activity = activity;
    }


    public void Signs() {
        intent = new Intent(activity, Signs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void Tests() {
        intent = new Intent(activity, TestListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void SMS(){
        intent = new Intent(activity, SendSmsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void NearByDrivingCenters(){
        intent = new Intent(activity, NearByDrivingCenters.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void UsefulTips() {

        intent = new Intent(activity, UsefulTipsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);

    }

    public void Register() {
        intent = new Intent(activity, Registration.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
    public void QuestionNAnswer() {
        intent = new Intent(activity, MockTestViewPager.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void LandingActivity() {
        intent = new Intent(activity, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void CourseMaterial() {
        intent = new Intent(activity, CourseMaterial.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void SubQstnAns() {
        intent = new Intent(activity, SubQstnAns.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void ObjQstnAns() {
        intent = new Intent(activity, ObjQstnAns.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void WebSite() {
        intent = new Intent(activity, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void UsefulVideo() {
        intent = new Intent(activity, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("type","youtube");
        activity.startActivity(intent);
    }

    public void SignsSub(String type) {
        Toast.makeText(activity, "Under Construction..", Toast.LENGTH_LONG).show();
//        intent = new Intent(activity, PdfActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        activity.startActivity(intent);
    }

    public void Remainder() {
        intent = new Intent(activity, ReminderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
}
