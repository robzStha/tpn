package com.official.trialpassnepal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.official.trialpassnepal.adapters.NavDrawerListAdapter;
import com.official.trialpassnepal.db.DbCategories;
import com.official.trialpassnepal.db.DbDrivingCenters;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.Data;
import com.official.trialpassnepal.objects.NavDrawerItems;
import com.official.trialpassnepal.objects.SyncData;
import com.official.trialpassnepal.retrofitConfig.RetrofitSingleton;
import com.official.trialpassnepal.utils.CircleTransform;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.CommonMethods;
import com.official.trialpassnepal.utils.ConnectionMngr;
import com.official.trialpassnepal.utils.CopyToDevice;
import com.official.trialpassnepal.utils.Opener;
import com.official.trialpassnepal.utils.SharedPreference;
import com.official.trialpassnepal.view.TextViewTypeFaced;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<NavDrawerItems> navDrawerItems;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 111;
    TextView tv_loading;
    static int selectedPage=0;

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
    private CopyToDevice copyToDevice;

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
        tv_loading = (TextView) findViewById(R.id.progress_bar);
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
        initPdf();
    }

    private void initPdf() {
        copyToDevice = new CopyToDevice(getApplicationContext());

        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                Toast.makeText(Dashboard.this, "show an explanation", Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(Dashboard.this, "show an explanation else", Toast.LENGTH_LONG).show();

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(BaseActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        } else {
            copyPDF();
        }


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

    public void setBackButton(){
        BACK_BUTTON_ENABLED = true;
        DRAWER_BUTTON_ENABLED = false;
        ibtnActionBtn.setImageResource(R.drawable.ic_arrow_back_white_24dp);
        ibtnActionBtn.setTag(BACK_BUTTON_ID);
        ibtnActionBtn.setOnClickListener(actionBarClickListener);
    }

    private void intiNavDrawer() {
        ivUserProfilePic = (ImageView) findViewById(R.id.iv_userProfilePic);
        tvUserName = (TextViewTypeFaced) findViewById(R.id.tv_username);
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
        mDrawerToggle = new ActionBarDrawerToggle(BaseActivity.this, mDrawerLayout, R.drawable.ic_menu_white_24dp, R.string.app_name, R.string.app_name) {
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        frameLayout.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        frameLayout.setVisibility(View.INVISIBLE);
        if(selectedPage==position){
            frameLayout.setVisibility(View.VISIBLE);
        }
        if (position == 0 && selectedPage!=0) { // Pathya kram samagri = Course material
            selectedPage=0;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.CourseMaterial();
                }
            }, 150);
        } else if (position == 1 && selectedPage!=1) {
            selectedPage=1;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.QuestionNAnswer();
                }
            }, 150);
        } else if (position == 2 && selectedPage!=2) {
            selectedPage=2;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.NearByDrivingCenters();
                }
            }, 500);
        } else if (position == 3 && selectedPage!=3) {
            selectedPage=3;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.SMS();
                }
            }, 150);
        } else if (position == 4 && selectedPage!=4) {
            selectedPage=4;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.Remainder();
                }
            }, 150);
        } else if (position == 5 && selectedPage!=5) {
            selectedPage = 5;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.UsefulTips();
                }
            }, 150);
        } else if (position == 6 && selectedPage!=6) {
            selectedPage = 6;
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    Uri data;
                    data = Uri.fromFile(new File(CommonDef.QUESTION_SAMPLE_LOC));
                    browserIntent.setDataAndType(data, "application/pdf");
                    startActivity(browserIntent);
                }
            }, 150);

        } else if (position == 7) {
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.WebSite();
                }
            }, 300);
        } else if (position == 8) {
            // trial video
            tv_loading.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    open.UsefulVideo();
                }
            }, 100);
        } else if (position == 9) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginManager.getInstance().logOut();
//                    prefs.setKeyValues(CommonDef.USER_INTEREST, "");
                    finish();
                    open.LandingActivity();
                }
            }, 100);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    void initCustomBar() {
        ibtnActionBtn = (ImageButton) findViewById(R.id.ibtn_action);
        tvActionTitle = (TextViewTypeFaced) findViewById(R.id.tv_action);
        tvActionTitle.setText(actionBarTitle());
        rlCustomActionBar = (RelativeLayout) findViewById(R.id.ll_custom_action_bar);
        enableDrawerButton();
    }

    public void setTitle(String title){
        tvActionTitle.setText(title);
    }

    public void hideMenu() {
        ibtnActionBtn.setVisibility(View.GONE);
    }



    public void enableDrawerButton() {
        BACK_BUTTON_ENABLED = false;
        DRAWER_BUTTON_ENABLED = true;
        ibtnActionBtn.setImageResource(R.drawable.ic_menu_white_24dp);
        ibtnActionBtn.setTag(DRAWER_ID);
        ibtnActionBtn.setOnClickListener(actionBarClickListener);
    }

    public View.OnClickListener actionBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag().equals(BACK_BUTTON_ID)) {
                finish();
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

                    System.out.println("This is the response error: " + response.errorBody());
                    System.out.println("This is the response: " + response.body().getCode());
                    System.out.println("response: " + response.errorBody());
                    SyncData syncData = response.body();
                    String newUpdateTimeStamp = syncData.getUpdatedDate();
                    prefs.setKeyValues(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP, newUpdateTimeStamp);

                    Data data = response.body().getData();
                    if (lastUpdateTimeStamp.equals("0")) {
                        dbCategories.truncate();
                        dbQuestionAnswer.truncate();
                        dbDrivingCenters.truncate();
                        CommonMethods.setDatabase(data, getApplicationContext());
                    }
                    progressDialog.dismiss();
                    finish();
                    open.CourseMaterial(); // after successful data sync open course materials
                }

                @Override
                public void onFailure(Call<SyncData> call, Throwable t) {
                    progressDialog.dismiss();
                    System.out.println("Error::: " + t.getMessage() + "---" + t.getLocalizedMessage());
                    finish();
                    open.CourseMaterial();
                }
            });
        } else
            open.CourseMaterial();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    copyPDF();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(BaseActivity.this, "permission denied", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void copyPDF() {
        CommonDef.DIRECTION_SIGN_LOC = copyToDevice.copy("direction_signs.pdf");
        CommonDef.INFORMATION_SIGN_LOC = copyToDevice.copy("information_signs.pdf");
        CommonDef.QUESTION_SAMPLE_LOC = copyToDevice.copy("question_sample.pdf");
        CommonDef.REGULATORY_SIGN_LOC = copyToDevice.copy("regulatory_signs.pdf");
        CommonDef.SAWARI_SADHAN_LOC = copyToDevice.copy("sawari_sadhan.pdf");
        CommonDef.SAWARIKO_NUMBER_PLATE_LOC = copyToDevice.copy("sawariko_number_plate.pdf");
        CommonDef.TRAFFIC_LICHT_SIGN_LOC = copyToDevice.copy("traffic_light_signs.pdf");
        CommonDef.WARNING_SIGN_LOC = copyToDevice.copy("warning_signs.pdf");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv_loading.setVisibility(View.GONE);
    }
}
