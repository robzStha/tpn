package com.official.trialpassnepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.official.trialpassnepal.adapters.CategoriesAdapter;
import com.official.trialpassnepal.adapters.CourseMaterialAdapter;

public class CourseMaterial extends BaseActivity {

    String[] courseMaterial = new String[3];
    private ListView lvCourseMaterial;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_material;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_course_material);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseMaterial[0] = getString(R.string.chinhaharu);
        courseMaterial[1] = getString(R.string.bisyagat_prashnaharu);
        courseMaterial[2] = getString(R.string.bastugat_prashnaharu);

        lvCourseMaterial = (ListView) findViewById(R.id.listView);
        lvCourseMaterial.setAdapter(new CourseMaterialAdapter(getApplicationContext(), courseMaterial));
        lvCourseMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        open.Signs();
                        break;
                    case 1:
                        open.SubQstnAns();
                        break;
                    case 2:
                        open.ObjQstnAns();
                        break;
                }
            }
        });
    }

}
