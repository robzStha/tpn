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

    ArrayList<QuestionObject> questionObjects;
    MockTestViewPager context;
    LayoutInflater inflater;
    ArrayList<String> answers;

    public FinalAnswersAdapter(ArrayList<QuestionObject> questionObjects, MockTestViewPager context, ArrayList<String> answers) {
        this.questionObjects = questionObjects;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        this.answers = answers;
    }

    @Override
    public int getCount() {
        return questionObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return questionObjects.get(position);
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

        tvQuestion.setText(questionObjects.get(position).getQuestion());
        tvAnswer.setText(answers.get(position));
        Integer key;
        Iterator myVeryOwnIterator = context.hmAnswers.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            key=(Integer)myVeryOwnIterator.next();
            HashMap<Integer, Boolean> answer = context.hmAnswers.get(key);
            Iterator answerIterator = answer.keySet().iterator();
            Integer ansKey;
            while(answerIterator.hasNext()){
                ansKey = (Integer) answerIterator.next();
                Boolean value = answer.get(ansKey);
                if(value){
                    ivCorrect.setVisibility(View.VISIBLE);
                }else{
                    ivCorrect.setVisibility(View.GONE);
                }
            }
        }
//        tvAnswer.setText(questionObjects.get(position).);

        return view;
    }
}
