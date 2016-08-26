package com.official.trialpassnepal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.official.trialpassnepal.db.DbCrud;
import com.official.trialpassnepal.utils.Opener;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.Arrays;

public class LandingActivity extends BaseActivity {

    ImageView ivUserProfilePic;
    TextViewTypeFaced tvUserName;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Opener opener;


    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_landing;
    }

    @Override
    protected String actionBarTitle() {
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        FacebookSdk.sdkInitialize(getApplicationContext());
        init();
        callbackManager = CallbackManager.Factory.create();
        fbLoginInit();

    }

    private void fbLoginInit() {


        // If using in a fragment
//        loginButton.setFragment(this);
        // Other app specific specialization


        // Callback registration


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("token: "+loginResult.getAccessToken());
                loginButton.setVisibility(View.GONE);
                finish();
//                syncData();
                opener.SelectUserInterest();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_button:
                    LoginManager.getInstance().logInWithReadPermissions(LandingActivity.this, Arrays.asList("public_profile"));
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private void init() {
        opener = new Opener(LandingActivity.this);
        setActionBarTransparent();
        frameLayout.removeAllViews();
        frameLayout = null;
        frameLayout = (FrameLayout) findViewById(R.id.fl_fullContainer);
        frameLayout.addView(view);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        hideNavigationDrawer();
        new DbCrud(LandingActivity.this);
    }

}