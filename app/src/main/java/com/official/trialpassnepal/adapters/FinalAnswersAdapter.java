package com.official.trialpassnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.objects.Question;
import com.official.trialpassnepal.objects.QuestionAnswer;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.staticValues.StaticObject;
import com.official.trialpassnepal.view.MockTestViewPager;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Robz on 8/5/2016.
 */
public class FinalAnswersAdapter extends BaseAdapter{

    MockTestViewPager context;
    LayoutInflater inflater;
    ArrayList<QuestionAnswer> questionAnswers;

    public FinalAnswersAdapter(MockTestViewPager context) {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.questionAnswers = context.questionAnswers;
    }

    @Override
    public int getCount() {
        return questionAnswers.size();
    }

    @Override
    public Object getItem(int position) {
        return questionAnswers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = inflater.inflate(R.layout.final_answer_list_item_view, parent, false);
        }
        TextView tvQuestion = (TextView) view.findViewById(R.id.tv_question);
        TextView tvAnswer = (TextView) view.findViewById(R.id.tv_answer);
        ImageView ivCorrect = (ImageView) view.findViewById(R.id.iv_correct);

        tvQuestion.setText(questionAnswers.get(position).getQuestion());
        tvAnswer.setText(questionAnswers.get(position).getAnswer());
        if (questionAnswers.get(position).isCorrect()) {
            ivCorrect.setVisibility(View.VISIBLE);
        } else {
            ivCorrect.setVisibility(View.GONE);
        }


//        tvAnswer.setText(questionObjects.get(position).);

        return view;
    }
}
