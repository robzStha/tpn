package com.official.trialpassnepal.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Robz Stha on 12/26/2014.
 */
public final class CommonDef {

//    public static final String DIRECTORY = Environment.getExternalStorageDirectory()+"/"+"SMP/";
    public static final String HOME_URL="http://www.trialpassnepal.com/";
    public static final String BASE_URL=HOME_URL+"krujAPI/";
    public static final String[] SIGNS = {"pratibandhtmak","sachet","suchana","dishasuchak"};
    //getData/krujtpn/0

//    public static class Dirs{
//        public static String SIGNATURE = "signature";
//        public static String FONTS = "fonts";
//    }

    public static class SharedPrefKeys {

        public static final String NEW_UPDATE_TIMESTAMP = "new_timeStamp";
        public static String IS_LOGGED_IN = "isLoggedIn";
        public static String USER_ID = "userId";
        public static String FULL_NAME = "fullName";
        public static String USER_EMAIL = "userEmail";  // this is the original username
        public static String CONTACT_NO = "contactNo";
        public static String ADDRESS = "address";
        public static String HASH_CODE = "hashCode";
        public static String DEVICE_ID = "deviceId";
        public static String PROFILE_IMG_URL = "profile_img_url";
        public static String SHARE_IMG_URL = "share_img_url";

        public static String ACCESS_TOKEN = "access_token";
        public static String IS_SESSION_RUNNING = "isSessionRunning";
        public static String CURRENT_LAT = "currentLoc";
        public static String CURRENT_LONG = "currentLong";
        public static String LAST_UPDATE_TIMESTAMP = "timestamp";
    }

    public static void setupUI(final Activity activity, View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(activity, innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

}
