package com.official.trialpassnepal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.official.trialpassnepal.utils.ConnectionMngr;
import com.official.trialpassnepal.view.TextViewTypeFaced;


public class TipsDetailActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 10;
    public static final String API_KEY = "AIzaSyBYzJtQ2INADJn4tviHxrUUqUhoy5r1sHE";
    private String videoUrl;
    ConnectionMngr connectionMngr;
    private TextViewTypeFaced tvActionTitle;
    private ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_detail);
        connectionMngr = new ConnectionMngr(TipsDetailActivity.this);
        tvActionTitle = (TextViewTypeFaced) findViewById(R.id.tv_action);
        tvActionTitle.setText("Useful Tips Details");

        ibtnBack = (ImageButton) findViewById(R.id.ibtn_action);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.ytb_player);
        youTubeView.initialize(API_KEY, this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            videoUrl = bundle.getString("video_url");
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if(connectionMngr.hasConnection()) {
            if (!wasRestored) {
                youTubePlayer.cueVideo(extractYTId(videoUrl));
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("YouTube Error (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public static String extractYTId(String ytUrl) {
        String vId = null;

        String[] separated = ytUrl.split("=");
//        separated[0]; // this will contain "Fruit"
        vId  = separated[1]; // this will contain " they taste good"

//        Pattern pattern = Pattern.compile(
//                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
//                Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(ytUrl);
//        if (matcher.matches()){
//            vId = matcher.group(1);
//        }
        System.out.println("Video id: "+ vId);
        return vId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.ytb_player);
    }
}
