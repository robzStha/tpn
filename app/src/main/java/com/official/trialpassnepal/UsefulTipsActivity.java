package com.official.trialpassnepal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        return "Useful Tips";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] sUsefulLinks = new String[] {
                "https://www.youtube.com/watch?v=z4UDNzXD3qA",
                "https://www.youtube.com/watch?v=KEI4qSrkPAs",
                "https://www.youtube.com/watch?v=IdneKLhsWOQ",
                "https://www.youtube.com/watch?v=RG6EOci0suI",
                "https://www.youtube.com/watch?v=lwynOkq8nm0"};

        alUsefulLinks.addAll( Arrays.asList(sUsefulLinks) );
        listView = (ListView) findViewById(R.id.lv_usefulTips);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sUsefulLinks);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UsefulTipsActivity.this, TipsDetailActivity.class);
                intent.putExtra("video_url", sUsefulLinks[i]);
                startActivity(intent);
            }
        });

    }
}
