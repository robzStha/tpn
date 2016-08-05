package com.official.trialpassnepal;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.fragments.MockTestFragment;
import com.official.trialpassnepal.fragments.SubQstnAnsFragment;
import com.official.trialpassnepal.objects.SubQstnAnsObject;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.EditTextTypeFaced;
import com.official.trialpassnepal.view.MockTestViewPager;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SubQstnAns extends AppCompatActivity implements SubQstnAnsFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private static ArrayList<SubQstnAnsObject> subQstnAnsObjects;
    private ButtonTypeFaced btnNext;
    private ButtonTypeFaced btnPrev;
    private TextViewTypeFaced tvPageInfo;
    private TextViewTypeFaced tvCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_qstn_ans);
        getSupportActionBar().setTitle(R.string.bisyagat_prashnaharu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        subQstnAnsObjects = new DbQuestionAnswer(getApplicationContext()).getSubQstnAns();// gets obj quesiton
        viewPager.setAdapter(new SubQstnAnsPagerAdapter(getSupportFragmentManager()));

        btnNext = (ButtonTypeFaced) findViewById(R.id.btn_next);
        btnPrev = (ButtonTypeFaced) findViewById(R.id.btn_prev);

        tvPageInfo = (TextViewTypeFaced) findViewById(R.id.tv_totalPages);
        tvCurrentPage = (TextViewTypeFaced) findViewById(R.id.tv_currentPage);
        tvPageInfo.setText(subQstnAnsObjects.size() + "");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = viewPager.getCurrentItem();
            switch (v.getId()) {
                case R.id.btn_next:
                    if (currentPage <= subQstnAnsObjects.size()) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SubQstnAns.this);
        final EditText etQuestNo = (EditText) view.findViewById(R.id.etQuestionNumber);
        builder.setView(etQuestNo);
        builder.setTitle("Go to Question Number");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!etQuestNo.getText().toString().isEmpty()) {
                    int questionNo = Integer.parseInt(etQuestNo.getText().toString());
                    if (questionNo > 0 && questionNo <= subQstnAnsObjects.size()) {
                        viewPager.setCurrentItem(questionNo - 1);
//                        tvCurrentPage.setText(questionNo + "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid question number between: 1- " + subQstnAnsObjects.size(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter a valid question number between: 1- " + subQstnAnsObjects.size(), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class SubQstnAnsPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = subQstnAnsObjects.size();

        public SubQstnAnsPagerAdapter(FragmentManager fragmentManager) {
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

            temp = subQstnAnsObjects.size() == position ? -1 : position;
            return SubQstnAnsFragment.newInstance(subQstnAnsObjects.get(position), temp, subQstnAnsObjects.size());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subQstnAnsObjects.clear();
    }
}
