
package com.official.trialpassnepal.view;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.official.trialpassnepal.BaseActivity;
import com.official.trialpassnepal.R;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.fragments.MockTestFragment;
import com.official.trialpassnepal.objects.QuestionAnswer;
import com.official.trialpassnepal.objects.QuestionObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MockTestViewPager extends BaseActivity implements MockTestFragment.OnFragmentInteractionListener {

    private static ViewPager viewPager;
    public static ArrayList<QuestionObject> alQuestionObjectTemp;
    private MyPagerAdapter adapterViewPager;
    public ArrayList<QuestionAnswer> questionAnswers = new ArrayList<>();

    public ArrayList<String> answers = new ArrayList<>();
    // to store the answer and to check if is correct or not.
    public HashMap<Integer, HashMap<Integer, Boolean>> hmAnswers = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mock_test_view_pager;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_mock_test);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        new GetQuestions().execute();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = alQuestionObjectTemp.size();

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            int temp;

            temp = alQuestionObjectTemp.size() == position ? -1 : position;
            return MockTestFragment.newInstance(alQuestionObjectTemp.get(position), temp, alQuestionObjectTemp.size());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    private class GetQuestions extends AsyncTask<Void, Void, Void> {
//        ProgressDialog progressDialog = new ProgressDialog(MockTestViewPager.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Loading..");
//            progressDialog.show();
//        }

        @Override
        protected Void doInBackground(Void... params) {
            alQuestionObjectTemp = new DbQuestionAnswer(getApplicationContext()).getQuestions("0");// gets obj quesiton
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapterViewPager);
            viewPager.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return true;
                }
            });
            viewPager.setOffscreenPageLimit(20);
//            progressDialog.dismiss();
        }
    }
}

