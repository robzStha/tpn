package com.official.trialpassnepal;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.official.trialpassnepal.adapters.TestAdapter;
import com.official.trialpassnepal.objects.TestObject;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;


public class TestListActivity extends BaseActivity {

    ListView lvTests;
    int offset = 0, limit = 6;
    final static int LIMIT = 6;
    ButtonTypeFaced btnNext, btnPrev;
    TextViewTypeFaced tvPageInfo;
    private TestAdapter testAdapter;
    private int totalItems = 55;
    private int portion = totalItems / limit;
    private int pages = (int) Math.ceil((double) totalItems / limit);
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

        intPagination();

        btnPrev.setOnClickListener(pagination);
        btnNext.setOnClickListener(pagination);

        for (int i = 0; i < totalItems; i++) {
            TestObject test = new TestObject();
            test.testName = "Test - title " + (i + 1);
            test.testId = (i + 1) + "";
            StaticObject.alTests.add(test);
        }

        ArrayList<TestObject> alTestObjectTemp = getTests();
        lvTests = (ListView) findViewById(R.id.lv_tests);
        testAdapter = new TestAdapter(getApplicationContext(), alTestObjectTemp);
        lvTests.setAdapter(testAdapter);
    }

    private ArrayList<TestObject> getTests() {
        ArrayList<TestObject> alTestObjectTemp = new ArrayList<>();
        for (int i = offset; i < (offset + limit); i++) {
            alTestObjectTemp.add(StaticObject.alTests.get(i));
        }
        return alTestObjectTemp;
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
                        testAdapter.resetAdapter(getTests());
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
                        testAdapter.resetAdapter(getTests());

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
