package com.official.trialpassnepal.adapters;

import android.content.Context;
import android.text.Html;
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
public class CourseMaterialAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    String[] courseMaterials;

    public CourseMaterialAdapter(Context context, String[] courseMaterials) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.courseMaterials = courseMaterials;
    }

    @Override
    public int getCount() {
        return courseMaterials.length;
    }

    @Override
    public Object getItem(int i) {
        return courseMaterials[i];
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
        ((TextViewTypeFaced)view.findViewById(R.id.tv_catTitle)).setText(Html.fromHtml(courseMaterials[pos]));
        return view;
    }
}
