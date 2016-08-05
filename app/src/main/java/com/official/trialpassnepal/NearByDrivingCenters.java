package com.official.trialpassnepal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.Nearby;
import com.official.trialpassnepal.db.DbDrivingCenters;
import com.official.trialpassnepal.objects.DrivingCenterObject;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.GoogleMapUtils;

import java.util.ArrayList;

public class NearByDrivingCenters extends BaseActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * @return the view to load in this activity.
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_near_by_driving_centers;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.title_activity_near_by_driving_centers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMapIfNeeded();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        if (GoogleMapUtils.isGPSandNetworkEnabled(getApplicationContext())) {
//            GoogleMapUtils.getCurrentLocation(mMap, NearByDrivingCenters.this);
            int service = GooglePlayServicesUtil.isGooglePlayServicesAvailable(NearByDrivingCenters.this);
            switch (service) {
                case ConnectionResult.SERVICE_MISSING:
                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                case ConnectionResult.SERVICE_DISABLED:

                    GooglePlayServicesUtil.getErrorDialog(service, NearByDrivingCenters.this, 2);
                    break;
                case ConnectionResult.SUCCESS:
                    mMap.setMyLocationEnabled(true);
//                    Location location = mMap.getMyLocation();
                    Location location = GoogleMapUtils.getMyLocation(NearByDrivingCenters.this);

                    if (location != null) {
                        GoogleMapUtils.loadLocation(mMap, new LatLng(location.getLatitude(), location.getLongitude()), true);
                    } else {
                        //for nepal
                        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(27.667984, 85.2790976), new LatLng(27.7499367, 85.37316799999999));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 1200, 1200, 10));
                    }
                    loadLocation(location);
            }
        } else {
            GoogleMapUtils.alertLocationSetting(NearByDrivingCenters.this);
        }

    }

    private void loadLocation(Location location) {
        ArrayList<DrivingCenterObject> alDrivingCenterObj = new DbDrivingCenters(getApplicationContext()).getNearestDrivingCenters(location, 1f);
        for (DrivingCenterObject drivingCenterObject : alDrivingCenterObj) {
            GoogleMapUtils.loadLocation(mMap, drivingCenterObject, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
//                    GoogleMapUtils.getCurrentLocation(mMap, NearByDrivingCenters.this);
                    mMap.setMyLocationEnabled(true);
//                    Location myLoc = mMap.getMyLocation();
                    Location location = GoogleMapUtils.getMyLocation(NearByDrivingCenters.this);
                    loadLocation(location);

                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NearByDrivingCenters Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.official.trialpassnepal/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NearByDrivingCenters Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.official.trialpassnepal/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
