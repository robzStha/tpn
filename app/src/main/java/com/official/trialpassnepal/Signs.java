package com.official.trialpassnepal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.official.trialpassnepal.adapters.CategoriesAdapter;
import com.official.trialpassnepal.db.DbCategories;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.CopyToDevice;

import java.io.File;


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
        setBackButton();
    }

    private void init() {
        DbCategories dbCategories = new DbCategories(getApplicationContext());

        StaticObject.alCategories = dbCategories.getCategories();
        lvCat = (ListView) findViewById(R.id.lv_cat);
        lvCat.setAdapter(new CategoriesAdapter(getApplicationContext()));
        lvCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String type = CommonDef.SIGNS[i];
//                open.SignsSub(type);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri data;
                switch(i){
                    case 0:
                        data = Uri.fromFile(new File(CommonDef.REGULATORY_SIGN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                    case 1:
                        data = Uri.fromFile(new File(CommonDef.WARNING_SIGN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                    case 2:
                        data = Uri.fromFile(new File(CommonDef.INFORMATION_SIGN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                    case 3:
                        data = Uri.fromFile(new File(CommonDef.DIRECTION_SIGN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                }
                startActivity(browserIntent);
            }
        });
    }
}
