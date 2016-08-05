package com.official.trialpassnepal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.official.trialpassnepal.adapters.CategoriesAdapter;
import com.official.trialpassnepal.db.DbCategories;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.utils.CommonDef;


public class Signs extends BaseActivity {

    ListView lvCat;
    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_categories;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_signs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        DbCategories dbCategories = new DbCategories(getApplicationContext());

        StaticObject.alCategories = dbCategories.getCategories();
        lvCat = (ListView) findViewById(R.id.lv_cat);
        lvCat.setAdapter(new CategoriesAdapter(getApplicationContext()));
        lvCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = CommonDef.SIGNS[i];
                open.SignsSub(type);
            }
        });
    }
}
