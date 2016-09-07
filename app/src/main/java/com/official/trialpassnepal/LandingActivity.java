package com.official.trialpassnepal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.official.trialpassnepal.db.DbCrud;
import com.official.trialpassnepal.service.SyncReceiver;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.CommonMethods;
import com.official.trialpassnepal.utils.Opener;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.http.Url;

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
        loginButton = (LoginButton) findViewById(R.id.login_button);
        // don't forget to give this.
        loginButton.setReadPermissions(Arrays.asList("public_profile,email,user_birthday"));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();
                fbLoginInit();
            }
        });
//    }

//    fbLoginInit();

//        test();
    }

    private void fbLoginInit() {

        // Callback registration

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request =
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject me, GraphResponse response) {
                                        String email1 = "adf";
                                        if (response.getError() != null) {
                                            System.out.println("Email: error: " + response.getError().toString());
                                            // handle error
                                        } else {
                                            email1 = response.getJSONObject().toString();
                                            String email = me.optString("email");
                                            String id = me.optString("id");
                                            System.out.println("Email: " + email + " id" + id + "----" + me.toString() + "  " + email1);
                                            // send email and id to your web server

                                            try {
                                                prefs.setKeyValues("user_name", me.getString("name").toString());
                                                prefs.setKeyValues("user_email", me.getString("email").toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            new MyAsyncTask().execute(CommonDef.HOME_URL + "/userprofile/getData/");

                                        }
                                    }
                                });
//                        .executeAsync();

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();


                // App code
                System.out.println("token: " + loginResult.getAccessToken());
                loginButton.setVisibility(View.GONE);
                // for first time synchronization..
                if (prefs.getStringValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP).equals("")) {
                    syncData();
                } else {
                    open.CourseMaterial();
                    finish();
                    Intent i = new Intent(LandingActivity.this, SyncReceiver.class);
                    i.putExtra("notification_id", CommonMethods.NOTIFICATION_ID);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(LandingActivity.this, CommonMethods.NOTIFICATION_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.SECOND, 10);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 10, pendingIntent);
                }
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
            switch (v.getId()) {
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

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                URL url = new URL(params[0]);
                postData(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {
//            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        public void postData(URL url) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("user_name", prefs.getStringValues("user_name"));
                connection.setRequestProperty("user_email", prefs.getStringValues("user_email"));
                connection.setRequestProperty("privateKey", "krujtpn");
                connection.setRequestProperty("user_interest", prefs.getStringValues(CommonDef.USER_INTEREST) + "");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                String urlParameters = "user_name=" + prefs.getStringValues("user_name") + ", user_email=" + prefs.getStringValues("user_email") + ", privateKey=" + "krujtpn" + ", user_interest=" + prefs.getStringValues(CommonDef.USER_INTEREST);
                DataOutputStream
                        dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters); //Writes out the string to the underlying output stream as a sequence of bytes<br />
                dStream.flush(); // Flushes the data output stream.<br />
                dStream.close(); // Closing the output stream.<br />
                System.out.println("Connection Code: " + urlParameters + " -- " + connection.getResponseCode() + "  " + connection.getResponseMessage().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}