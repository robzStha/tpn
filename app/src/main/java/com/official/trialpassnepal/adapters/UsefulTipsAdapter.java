package com.official.trialpassnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.view.TextViewTypeFaced;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class UsefulTipsAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    String[] items;

    public UsefulTipsAdapter(Context context, String[] items) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.cat_list_item, viewGroup, false);
        }
        ((TextViewTypeFaced)view.findViewById(R.id.tv_catTitle)).setText(items[pos]);
        return view;
    }
}
