package com.official.trialpassnepal.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;

import com.facebook.Profile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SlowhandJr. on 7/15/16.
 */
public class CommonMethods {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static boolean isLoggedIn() {
        return Profile.getCurrentProfile() != null;
    }

    public Calendar convertMilliSecToDateTime(String milliSec) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(milliSec));
        return cl;
    }

}
