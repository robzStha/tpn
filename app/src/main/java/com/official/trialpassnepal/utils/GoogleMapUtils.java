package com.official.trialpassnepal.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.official.trialpassnepal.R;
import com.official.trialpassnepal.objects.DrivingCenterObject;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class GoogleMapUtils {

    public static void alertLocationSetting(final Activity activity) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage(activity.getResources().getString(R.string.gps_network_not_enabled));
        dialog.setPositiveButton(activity.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivityForResult(myIntent, 1);
                //get gps
            }
        });
        dialog.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }

    public static boolean isGPSandNetworkEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            return false;
        }
        return true;
    }

    public static void getCurrentLocation(final GoogleMap mMap, final Activity activity){
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage(activity.getString(R.string.msg_loading));
        pd.show();
        final SharedPreference prefs = new SharedPreference(activity);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                prefs.setKeyValues(CommonDef.SharedPrefKeys.CURRENT_LAT, location.getLatitude()+"");
                prefs.setKeyValues(CommonDef.SharedPrefKeys.CURRENT_LONG, location.getLongitude()+"");
                if(pd.isShowing())
                    pd.dismiss();

                System.out.println("Location: " + location.getLatitude() + "" + location.getLongitude());
                Toast.makeText(activity, "Location: " + location.getLatitude() + "" + location.getLongitude(), Toast.LENGTH_LONG).show();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                GoogleMapUtils.loadLocation(mMap, latLng , false);
//                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLatitude())).title("Marker"));
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pd.isShowing()){
                    pd.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Cannot load location. Please find a open place to load location.");
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        }, 30000);
    }

    public static void loadLocation(GoogleMap mMap, LatLng latLng, boolean animate){
        if(animate) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(13).build();

//            CameraUpdate center =CameraUpdateFactory.newLatLng(latLng);
//            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
//
//            mMap.moveCamera(center);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions marker = new MarkerOptions().position(latLng).title("My Location");

// Changing marker icon
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(marker);
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng).title("Driving Center"));
        }
    }
    public static void loadLocation(GoogleMap mMap, DrivingCenterObject drivingCenterObject, boolean animate){
        LatLng latLng = new LatLng(drivingCenterObject.getLat(), drivingCenterObject.getLng());
        if(animate) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(13).build();

//            CameraUpdate center =CameraUpdateFactory.newLatLng(latLng);
//            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
//
//            mMap.moveCamera(center);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions marker = new MarkerOptions().position(latLng).title("My Location");

// Changing marker icon
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(marker);
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng).title(drivingCenterObject.getDcName()).snippet(drivingCenterObject.getDcProprietor()));
        }
    }

    public static Location getMyLocation(Context context) {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
    }
}
