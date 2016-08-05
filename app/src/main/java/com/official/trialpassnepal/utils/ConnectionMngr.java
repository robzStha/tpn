package com.official.trialpassnepal.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import com.official.trialpassnepal.R;


public class ConnectionMngr {

    private static Context _cntx;

	public ConnectionMngr(Context cntx){
		_cntx = cntx;
	}
	
	public boolean hasConnection(){
		ConnectivityManager cm = (ConnectivityManager) _cntx.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected())
			return true;
		else{
			AlertDialog.Builder dialogNetworkState = new AlertDialog.Builder(
					_cntx);
        	
        	dialogNetworkState.setTitle(_cntx.getResources().getString(R.string.app_name))
        				.setMessage("Please connect your device to the internet and Try Again")
//        				.setIcon(android.R.drawable.ic_delete)
//        				.setIcon(R.drawable.ic_launcher)
        				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
        	
        	AlertDialog dialog = dialogNetworkState.create();
        	dialog.show();
        	return false;
		}
	}

}
