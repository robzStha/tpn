package com.official.trialpassnepal;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.official.trialpassnepal.utils.CommonDef;

public class WebViewActivity extends BaseActivity {

    private WebView webview;
    private ProgressDialog progressBar;
    ImageButton btnBack;
    String mUrl = "http://www.trialpassnepal.com/";
    private String type="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.website);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.webview = (WebView)findViewById(R.id.webview);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            type = bundle.getString("type");
        }

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                builder.setTitle("Error");
                builder.setMessage(description);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });
//        webview.loadUrl("http://nepaknol.net/cdc/ebooks/");
        if(type.equals("")) {
            webview.loadUrl("http://www.trialpassnepal.com");
        }else if(type.equals("youtube")){
            setTitle(getString(R.string.trial_video));
            mUrl = "https://www.youtube.com/channel/UCynOruPGMPVeip5jndyvusA";
            webview.loadUrl(mUrl);
        }else if(type.equals(CommonDef.SIGNS[0])){
            webview.loadUrl("http://docs.google.com/gview?embedded=true&url="+"file:///android_asset/regulatory_signs.pdf");
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webview.canGoBack()){
                    webview.goBack();
                }
            }
        });
    }


}
