package com.official.trialpassnepal.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;

import com.facebook.Profile;
import com.official.trialpassnepal.db.DbCategories;
import com.official.trialpassnepal.db.DbDrivingCenters;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.Category;
import com.official.trialpassnepal.objects.Data;
import com.official.trialpassnepal.objects.DrivingCenter;
import com.official.trialpassnepal.objects.Question;
import com.official.trialpassnepal.objects.QuestionImage;
import com.official.trialpassnepal.objects.QuestionOption;
import com.official.trialpassnepal.objects.SubAnswer;
import com.official.trialpassnepal.objects.SyncData;
import com.official.trialpassnepal.retrofitConfig.RetrofitSingleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SlowhandJr. on 7/15/16.
 */
public class CommonMethods {

    public static final int NOTIFICATION_ID = 23;

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

    public static void syncData(final Context context) {
        final SharedPreference prefs = new SharedPreference(context);
        final DbCategories dbCategories = new DbCategories(context);
        final DbQuestionAnswer dbQuestionAnswer = new DbQuestionAnswer(context);
        final DbDrivingCenters dbDrivingCenters = new DbDrivingCenters(context);
        String temp = prefs.getStringValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP);
        temp = temp.equals("") ? "0" : temp;
        final String lastUpdateTimeStamp = temp;
        Call<SyncData> call = RetrofitSingleton.getApiService().getSyncData(lastUpdateTimeStamp); // 0 for first time sync
        call.enqueue(new Callback<SyncData>() {
            @Override
            public void onResponse(Call<SyncData> call, Response<SyncData> response) {

                System.out.println("This is the response error: " + response.errorBody());
                System.out.println("This is the response: " + response.body().getCode());
                System.out.println("response: " + response.errorBody());
                SyncData syncData = response.body();
                String newUpdateTimeStamp = syncData.getUpdatedDate();
                prefs.setKeyValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP, newUpdateTimeStamp);

                int iLastUpdateTimeStamp = Integer.parseInt(lastUpdateTimeStamp);
                int iNewUpdateTimeStamp = Integer.parseInt(newUpdateTimeStamp);
                Data data = response.body().getData();
                if (iLastUpdateTimeStamp < iNewUpdateTimeStamp) {
                    dbCategories.truncate();
                    dbQuestionAnswer.truncate();
                    dbDrivingCenters.truncate();
                    setDatabase(data, context);
                }
            }

            @Override
            public void onFailure(Call<SyncData> call, Throwable t) {
                System.out.println("Error::: " + t.getMessage() + "---" + t.getLocalizedMessage());

            }
        });
    }

    public static void setDatabase(Data data, Context context) {
        DbCategories dbCategories = new DbCategories(context);
        DbQuestionAnswer dbQuestionAnswer = new DbQuestionAnswer(context);
        DbDrivingCenters dbDrivingCenters = new DbDrivingCenters(context);
        dbCategories.insertCategories((ArrayList<Category>) data.getCategory());
        dbQuestionAnswer.insertQuestions((ArrayList<Question>) data.getQuestions());
        dbQuestionAnswer.insertQuestionOptions((ArrayList<QuestionOption>) data.getQuestionOptions());
        dbQuestionAnswer.insertQuestionImages((ArrayList<QuestionImage>) data.getQuestionImages());
        dbQuestionAnswer.insertSubAnswers((ArrayList<SubAnswer>) data.getSubAnswers());
        dbDrivingCenters.insertDrivingCenters((ArrayList<DrivingCenter>) data.getDrivingCenters());
    }

}
