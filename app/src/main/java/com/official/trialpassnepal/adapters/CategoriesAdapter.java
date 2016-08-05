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
public class CategoriesAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;

    public CategoriesAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return StaticObject.alCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return StaticObject.alCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return StaticObject.alCategories.get(i).catId;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.cat_list_item, viewGroup, false);
        }
        ((TextViewTypeFaced)view.findViewById(R.id.tv_catTitle)).setText(StaticObject.alCategories.get(pos).catName);
        return view;
    }
}
