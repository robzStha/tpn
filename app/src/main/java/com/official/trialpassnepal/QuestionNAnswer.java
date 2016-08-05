package com.official.trialpassnepal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.official.trialpassnepal.adapters.QuestionNAnswerAdapter;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;


public class QuestionNAnswer extends BaseActivity {

    ListView lvTests;
    int offset = 0, limit = 1;
    final static int LIMIT = 1;
    ButtonTypeFaced btnNext, btnPrev;
    TextViewTypeFaced tvPageInfo;
    private QuestionNAnswerAdapter questionNAnswerAdapter;
    private int totalItems;
    private int portion;
    private int pages;
    private int currentPage = 1;
    boolean noNext, noPrev;

    TextViewTypeFaced tvCurrentPage, tvTotalPages;

    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_list;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_test_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        btnNext = (ButtonTypeFaced) findViewById(R.id.btn_next);
        btnPrev = (ButtonTypeFaced) findViewById(R.id.btn_prev);

        tvPageInfo = (TextViewTypeFaced) findViewById(R.id.tv_totalPages);

        ArrayList<QuestionObject> alQuestionObjectTemp = new DbQuestionAnswer(getApplicationContext()).getQuestions("0");// gets obj quesiton
        for (QuestionObject test : alQuestionObjectTemp) {
//            QuestionObject test = new QuestionObject();
            test.setQuestion(test.getQuestion());
            test.setQid(test.getQid());
            StaticObject.alQuestion.add(test);
        }
        totalItems = alQuestionObjectTemp.size();
        portion = totalItems / limit;
        pages = (int) Math.ceil((double) totalItems / limit);

        intPagination();

        btnPrev.setOnClickListener(pagination);
        btnNext.setOnClickListener(pagination);


        lvTests = (ListView) findViewById(R.id.lv_tests);
        questionNAnswerAdapter = new QuestionNAnswerAdapter(getApplicationContext(), alQuestionObjectTemp);
        lvTests.setAdapter(questionNAnswerAdapter);
    }

    private ArrayList<QuestionObject> getTests() {
        ArrayList<QuestionObject> alQuestionObjectTemp = new ArrayList<>();
        for (int i = offset; i < (offset + limit); i++) {
            alQuestionObjectTemp.add(StaticObject.alQuestion.get(i));
        }
        return alQuestionObjectTemp;
    }

    void intPagination() {
        tvCurrentPage = (TextViewTypeFaced) findViewById(R.id.tv_currentPage);
        tvTotalPages = (TextViewTypeFaced) findViewById(R.id.tv_totalPages);

        tvTotalPages.setText(" / " + pages);
        setPagination();
    }

    private View.OnClickListener pagination = new View.OnClickListener() {


        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_next:
                    if (!noNext) {
                        offset = (offset + limit);
                        if (offset / limit == portion) {
                            limit = (totalItems % limit);
                            noNext = true;
//                            currentPage++;
                        }

                        noPrev = false;
                        questionNAnswerAdapter.resetAdapter(getTests());
                    }
                    break;
                case R.id.btn_prev:
                    limit = LIMIT;
                    if (offset < limit) {
                        noPrev = true;
                    }
                    if (!noPrev) {
                        noNext = false;
                        offset = (offset - limit);
                        questionNAnswerAdapter.resetAdapter(getTests());

                    }
                    break;
            }
            setPagination();
        }
    };

    private void setPagination() {
        currentPage = offset / LIMIT;
        tvCurrentPage.setText((currentPage + 1) + "");
    }
}
