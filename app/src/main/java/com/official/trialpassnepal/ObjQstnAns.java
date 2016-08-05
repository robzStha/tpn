package com.official.trialpassnepal;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.fragments.MockTestFragment;
import com.official.trialpassnepal.fragments.ObjQstnAnsFragment;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjQstnAns extends BaseActivity implements ObjQstnAnsFragment.OnFragmentInteractionListener{

    private static ViewPager viewPager;
    static ArrayList<QuestionObject> alQuestionObjectTemp;
    private MyPagerAdapter adapterViewPager;
    // to store the answer and to check if is correct or not.
    public HashMap<Integer, HashMap<Integer, Boolean>> hmAnswers = new HashMap<>();
    private ButtonTypeFaced btnNext;
    private ButtonTypeFaced btnPrev;
    private TextViewTypeFaced tvPageInfo;
    private TextViewTypeFaced tvCurrentPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_obj_qstn_ans;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.bastugat_prashnaharu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        alQuestionObjectTemp = new DbQuestionAnswer(getApplicationContext()).getQuestions();// gets obj quesiton
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        btnNext = (ButtonTypeFaced) findViewById(R.id.btn_next);
        btnPrev = (ButtonTypeFaced) findViewById(R.id.btn_prev);

        tvPageInfo = (TextViewTypeFaced) findViewById(R.id.tv_totalPages);
        tvCurrentPage = (TextViewTypeFaced) findViewById(R.id.tv_currentPage);
        tvPageInfo.setText(alQuestionObjectTemp.size() + "");
        tvCurrentPage.setText((viewPager.getCurrentItem() + 1) + "");

        btnNext.setOnClickListener(clickListener);
        btnPrev.setOnClickListener(clickListener);
        tvCurrentPage.setOnClickListener(clickListener);
//        viewPager.setCurrentItem(7, true);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCurrentPage.setText((viewPager.getCurrentItem() + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = viewPager.getCurrentItem();
            switch (v.getId()) {
                case R.id.btn_next:
                    if (currentPage <= alQuestionObjectTemp.size()) {
                        viewPager.setCurrentItem(currentPage + 1);
                    }
                    break;
                case R.id.btn_prev:
                    if (currentPage >= 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                    break;
                case R.id.tv_currentPage:
                    alertGotoQuestion();
                    break;
            }
        }
    };

    private void alertGotoQuestion() {
        LayoutInflater inflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_edittext, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(ObjQstnAns.this);
        final EditText etQuestNo = (EditText) view.findViewById(R.id.etQuestionNumber);
        builder.setView(etQuestNo);
        builder.setTitle("Go to Question Number");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!etQuestNo.getText().toString().isEmpty()) {
                    int questionNo = Integer.parseInt(etQuestNo.getText().toString());
                    if (questionNo > 0 && questionNo <= alQuestionObjectTemp.size()) {
                        viewPager.setCurrentItem(questionNo - 1);
//                        tvCurrentPage.setText(questionNo + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid question number between: 1- " + alQuestionObjectTemp.size(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter a valid question number between: 1- " + alQuestionObjectTemp.size(), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create().show();
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
            return ObjQstnAnsFragment.newInstance(alQuestionObjectTemp.get(position), temp, alQuestionObjectTemp.size());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
