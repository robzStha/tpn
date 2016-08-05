package com.official.trialpassnepal;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.official.trialpassnepal.service.UpdatePullService;
import com.official.trialpassnepal.utils.CommonMethods;
import com.official.trialpassnepal.utils.Opener;

public class SplashActivity extends AppCompatActivity {

    AnimationDrawable rocketAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView rocketImage = (ImageView) findViewById(R.id.iv_loading);
        rocketImage.setBackgroundResource(R.drawable.splash_anim);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.start();
        onAnimationFinish(rocketAnimation);

        Intent intentService = new Intent(SplashActivity.this, UpdatePullService.class);
        intentService.setData(Uri.parse("tset"));
    }

    private void onAnimationFinish(AnimationDrawable anim) {
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)) {
                    onAnimationFinish(a);
                } else {
                    if(!CommonMethods.isLoggedIn()) {
                        Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                        startActivity(i);
                    }else{
                        new Opener(SplashActivity.this).CourseMaterial();
                    }
                    finish();
                }
            }
        }, timeBetweenChecks);
    }
}
