package com.official.trialpassnepal;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.fragments.MockTestFragment;
import com.official.trialpassnepal.objects.QuestionObject;

import java.util.ArrayList;

public class MockTest extends BaseActivity implements MockTestFragment.OnFragmentInteractionListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mock_test;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_mock_test);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<QuestionObject> alQuestionObjectTemp = new DbQuestionAnswer(getApplicationContext()).getQuestions("0");// gets obj quesiton
        Fragment newFragment = new MockTestFragment().newInstance(alQuestionObjectTemp.get(0), 1, 0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, newFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("URI: "+uri.toString());
    }
}
