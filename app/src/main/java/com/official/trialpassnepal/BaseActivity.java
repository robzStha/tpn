package com.official.trialpassnepal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.official.trialpassnepal.adapters.NavDrawerListAdapter;
import com.official.trialpassnepal.db.DbCategories;
import com.official.trialpassnepal.db.DbDrivingCenters;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.Category;
import com.official.trialpassnepal.objects.Data;
import com.official.trialpassnepal.objects.DrivingCenter;
import com.official.trialpassnepal.objects.NavDrawerItems;
import com.official.trialpassnepal.objects.Question;
import com.official.trialpassnepal.objects.QuestionImage;
import com.official.trialpassnepal.objects.QuestionOption;
import com.official.trialpassnepal.objects.SubAnswer;
import com.official.trialpassnepal.objects.SyncData;
import com.official.trialpassnepal.retrofitConfig.RetrofitSingleton;
import com.official.trialpassnepal.utils.CircleTransform;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.CommonMethods;
import com.official.trialpassnepal.utils.ConnectionMngr;
import com.official.trialpassnepal.utils.Opener;
import com.official.trialpassnepal.utils.SharedPreference;
import com.official.trialpassnepal.view.TextViewTypeFaced;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<NavDrawerItems> navDrawerItems;
    private String[] navMenuTitles;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mainView;
    LayoutInflater inflater;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ConnectionMngr connectionMngr;

    public static boolean BACK_BUTTON_ENABLED = false;
    public static boolean DRAWER_BUTTON_ENABLED = false;
    public String DRAWER_ID = "001";
    public String BACK_BUTTON_ID = "002";
    public static boolean ACTION_BAR_ENABLED = true;

    ActionBar actionBar;

    public ImageButton ibtnActionBtn;

    SharedPreference prefs;

    Opener open;
    public RelativeLayout rlCustomActionBar;
    private TextViewTypeFaced tvActionTitle;
    public FrameLayout frameLayout;
    public View view;
    private ImageView ivUserProfilePic;
    private TextViewTypeFaced tvUserName;
    private ProfileTracker mProfileTracker;
    private Profile mainProfile;
    private DbCategories dbCategories;
    private DbQuestionAnswer dbQuestionAnswer;
    private DbDrivingCenters dbDrivingCenters;

    /**
     * @return the view to load in this activity.
     */
    protected abstract int getLayoutId();

    protected abstract String actionBarTitle();
//    protected abstract boolean isActionBarEnabled();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        actionBar = getSupportActionBar();
        actionBar.hide();

        connectionMngr = new ConnectionMngr(BaseActivity.this);
        open = new Opener(this);
        prefs = new SharedPreference(this);
        inflater = (LayoutInflater) getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        intiNavDrawer();
        initCustomBar();

        if (CommonMethods.isLoggedIn()) {
            setNavUser();
        }

        dbCategories = new DbCategories(getApplicationContext());
        dbQuestionAnswer = new DbQuestionAnswer(getApplicationContext());
        dbDrivingCenters = new DbDrivingCenters(getApplicationContext());
        view = inflater.inflate(getLayoutId(), null, false);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        frameLayout.addView(view);

        CommonDef.setupUI(BaseActivity.this, frameLayout);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        };

        new Handler().postDelayed(r, 5000);
    }

    public void setNavUser() {
        final int imgSize = CommonMethods.convertPixelsToDp(200, getApplicationContext());

        if (Profile.getCurrentProfile() == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                    // profile2 is the new profile
                    Log.v("facebook - profile", profile2.getFirstName());
                    mainProfile = profile2;
                    mProfileTracker.stopTracking();

                    Picasso.with(getApplicationContext()).load(mainProfile.getProfilePictureUri(imgSize, imgSize)).transform(new CircleTransform()).into(ivUserProfilePic);
                    tvUserName.setText(mainProfile.getName());
                }
            };
            mProfileTracker.startTracking();
            // no need to call startTracking() on mProfileTracker
            // because it is called by its constructor, internally.
        } else {
            mainProfile = Profile.getCurrentProfile();
            Log.v("facebook - profile", mainProfile.getFirstName());
            Picasso.with(getApplicationContext()).load(mainProfile.getProfilePictureUri(imgSize, imgSize)).transform(new CircleTransform()).into(ivUserProfilePic);
            tvUserName.setText(mainProfile.getName());
        }


    }

    private void intiNavDrawer() {

        ivUserProfilePic = (ImageView) findViewById(R.id.iv_userProfilePic);
        tvUserName = (TextViewTypeFaced) findViewById(R.id.tv_username);
//        Picasso.with(getApplicationContext()).load(R.drawable.profile).transform(new CircleTransform()).into(ivUserProfilePic);
//        tvUserName.setText("John Doe");
        navMenuTitles = getResources().getStringArray(R.array.nav_menu);
        navDrawerItems = new ArrayList<>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainView = (RelativeLayout) findViewById(R.id.holder);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        for (String titles : navMenuTitles) {
            navDrawerItems.add(new NavDrawerItems(titles));
        }

        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        //shifting the mainview along with the sliding menu
        mDrawerToggle = new ActionBarDrawerToggle(BaseActivity.this, mDrawerLayout, R.drawable.drawer_icon_dark, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
//                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, "Position= " + position, Toast.LENGTH_SHORT).show();
        if (position == 0) { // Pathya kram samagri = Course material
            open.CourseMaterial();
        } else if (position == 1) {
            open.QuestionNAnswer();
        } else if (position == 2) {
            open.NearByDrivingCenters();
        } else if (position == 3) {
            open.SMS();
        } else if (position == 4) {
            // remainder
        } else if (position == 5) {
            open.UsefulTips();
        } else if (position == 6) {
            open.WebSite();
        } else if (position == 7) {
            // trial video
            open.UsefulVideo();
        } else if (position == 8) {
            LoginManager.getInstance().logOut();
            finish();
            open.LandingActivity();
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
            mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    void initCustomBar() {
        ibtnActionBtn = (ImageButton) findViewById(R.id.ibtn_action);
        tvActionTitle = (TextViewTypeFaced) findViewById(R.id.tv_action);
        tvActionTitle.setText(actionBarTitle());
        rlCustomActionBar = (RelativeLayout) findViewById(R.id.ll_custom_action_bar);
        enableDrawerButton();
    }

    public void enableDrawerButton() {
        BACK_BUTTON_ENABLED = false;
        DRAWER_BUTTON_ENABLED = true;
        ibtnActionBtn.setImageResource(R.drawable.drawer_icon_dark);
        ibtnActionBtn.setTag(DRAWER_ID);
        ibtnActionBtn.setOnClickListener(actionBarClickListener);
    }

    public View.OnClickListener actionBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals(BACK_BUTTON_ID)) {
                NavUtils.navigateUpFromSameTask(BaseActivity.this);
            } else if (v.getTag().equals(DRAWER_ID)) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        }
    };

    public void setActionBarTextGravity(int gravity) {
        if (tvActionTitle != null) {
            tvActionTitle.setGravity(gravity);
        }
    }

    public void setActionBarTransparent() {
        rlCustomActionBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    public void setActionBarBg(int color) {
        try {
            rlCustomActionBar.setBackgroundColor(getResources().getColor(color));
            tvActionTitle.setTextColor(getResources().getColor(R.color.dark));
            ibtnActionBtn.setImageResource(R.drawable.drawer_icon_dark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearUserInfo() {
        prefs.setKeyValues(CommonDef.SharedPrefKeys.IS_LOGGED_IN, false);
        prefs.setKeyValues(CommonDef.SharedPrefKeys.USER_ID, "");
        prefs.setKeyValues(CommonDef.SharedPrefKeys.HASH_CODE, "");
        prefs.setKeyValues(CommonDef.SharedPrefKeys.USER_EMAIL, "");
        prefs.setKeyValues(CommonDef.SharedPrefKeys.PROFILE_IMG_URL, "");
        prefs.setKeyValues(CommonDef.SharedPrefKeys.FULL_NAME, "");
        prefs.setKeyValues(CommonDef.SharedPrefKeys.ACCESS_TOKEN, "");
    }

    public void hideNavigationDrawer() {
        ibtnActionBtn.setVisibility(View.GONE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void syncData() {
        if (connectionMngr.hasConnection()) {
            final ProgressDialog progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setMessage("Synchronizing data from server.");
            progressDialog.show();
            String temp = prefs.getStringValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP);
            temp = temp.equals("") ? "0" : temp;
            final String lastUpdateTimeStamp = temp;
            Call<SyncData> call = RetrofitSingleton.getApiService().getSyncData(lastUpdateTimeStamp); // 0 for first time sync
            call.enqueue(new Callback<SyncData>() {
                @Override
                public void onResponse(Call<SyncData> call, Response<SyncData> response) {
                    System.out.println("This is the response: " + response.body().getCode());
                    System.out.println("response: "+response.errorBody());
                    SyncData syncData = response.body();
                    String newUpdateTimeStamp = syncData.getUpdatedDate();
                    prefs.setKeyValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP, newUpdateTimeStamp);

                    Data data = response.body().getData();
                    if (lastUpdateTimeStamp.equals("0")) {
                        dbCategories.truncate();
                        dbQuestionAnswer.truncate();
                        dbDrivingCenters.truncate();
                    }
                    setDatabase(data);
                    progressDialog.dismiss();
                    finish();
                    open.CourseMaterial(); // after successful data sync open course materials
                }

                @Override
                public void onFailure(Call<SyncData> call, Throwable t) {
                    progressDialog.dismiss();
                    System.out.println("Error::: " + t.getMessage()+"---"+t.getLocalizedMessage());

                }
            });
        } else
            open.CourseMaterial();
    }

    private void setDatabase(Data data) {
        dbCategories.insertCategories((ArrayList<Category>) data.getCategory());
        dbQuestionAnswer.insertQuestions((ArrayList<Question>) data.getQuestions());
        dbQuestionAnswer.insertQuestionOptions((ArrayList<QuestionOption>) data.getQuestionOptions());
        dbQuestionAnswer.insertQuestionImages((ArrayList<QuestionImage>) data.getQuestionImages());
        dbQuestionAnswer.insertSubAnswers((ArrayList<SubAnswer>) data.getSubAnswers());
        dbDrivingCenters.insertDrivingCenters((ArrayList<DrivingCenter>) data.getDrivingCenters());
    }
}
