package com.official.trialpassnepal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.official.trialpassnepal.adapters.CategoriesAdapter;
import com.official.trialpassnepal.adapters.UsefulTipsAdapter;
import com.official.trialpassnepal.utils.CommonDef;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class UsefulTipsActivity extends BaseActivity {


    ListView listView;
    ArrayList<String> alUsefulLinks = new ArrayList<>();
    private ArrayAdapter<String> listAdapter ;

    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_useful;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_useful);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] sUsefulLinks = getResources().getStringArray(R.array.useful_tips);

        alUsefulLinks.addAll( Arrays.asList(sUsefulLinks) );
        listView = (ListView) findViewById(R.id.lv_usefulTips);
        UsefulTipsAdapter adapter = new UsefulTipsAdapter(getApplicationContext(), sUsefulLinks);
//        listAdapter = new ArrayAdapter<>(this, R.layout.cat_list_item, sUsefulLinks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                Uri data;
                switch(i){
                    case 0:
                        data = Uri.fromFile(new File(CommonDef.SAWARI_SADHAN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                    case 1:
                        data = Uri.fromFile(new File(CommonDef.SAWARIKO_NUMBER_PLATE_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                    case 2:
                        data = Uri.fromFile(new File(CommonDef.TRAFFIC_LICHT_SIGN_LOC));
                        browserIntent.setDataAndType(data, "application/pdf");
                        break;
                }
                startActivity(browserIntent);
            }
        });

    }
}
