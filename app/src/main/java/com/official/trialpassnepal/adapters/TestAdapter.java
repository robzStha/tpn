package com.official.trialpassnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.objects.TestObject;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class TestAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<TestObject> alTests;

    public TestAdapter(Context context, ArrayList<TestObject> alTests) {
        this.alTests = alTests;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alTests.size();
    }

    @Override
    public Object getItem(int i) {
        return alTests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(alTests.get(i).testId);
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.test_list_item, viewGroup, false);
        }
        ((TextViewTypeFaced)view.findViewById(R.id.tv_question)).setText(alTests.get(pos).testName);
        return view;
    }

    public void resetAdapter(ArrayList<TestObject> tests) {
        alTests = new ArrayList<>();
        alTests = tests;
        notifyDataSetChanged();
    }
}
