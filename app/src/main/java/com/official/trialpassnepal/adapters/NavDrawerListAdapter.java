package com.official.trialpassnepal.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.objects.NavDrawerItems;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.utils.SharedPreference;

import java.util.ArrayList;


/**
 * Created by user on 7/23/2015.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItems> navDrawerItems;
    private SharedPreference prefs;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItems> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        prefs = new SharedPreference(context);
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

//        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.drawer_list_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.drawer_list_item);


//        if(imgIcon != null) {
//            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
//        }

        if(txtTitle != null) {
            txtTitle.setText(navDrawerItems.get(position).getTitle());
            FrameLayout flPopup = (FrameLayout) convertView.findViewById(R.id.fl_popupCircle);
            if(navDrawerItems.get(position).getTitle().equals("STORE")){
                flPopup.setVisibility(View.VISIBLE);
            }else{
                flPopup.setVisibility(View.GONE);
            }
            if(navDrawerItems.get(position).getTitle().equals("LOGOUT")){
                if(!prefs.getBoolValues(CommonDef.SharedPrefKeys.IS_LOGGED_IN)){
                    txtTitle.setTextColor(context.getResources().getColor(R.color.abc_search_url_text));
                }else{
                    txtTitle.setTextColor(context.getResources().getColor(R.color.abc_search_url_text_normal));
                }
            }
        }



        return convertView;
    }
}
