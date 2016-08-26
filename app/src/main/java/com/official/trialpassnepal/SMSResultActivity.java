package com.official.trialpassnepal;

import android.os.Bundle;
import android.view.View;

import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.TextViewTypeFaced;


public class SMSResultActivity extends BaseActivity {

    TextViewTypeFaced tvMsg;
    ButtonTypeFaced btnBack;

    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_smsresult;
    }

    @Override
    protected String actionBarTitle() {
        return "सवारी कर चेक ";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvMsg = (TextViewTypeFaced) findViewById(R.id.tv_msg);
        btnBack = (ButtonTypeFaced) findViewById(R.id.btn_back);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            tvMsg.setText(bundle.getString("sms_str"));
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
