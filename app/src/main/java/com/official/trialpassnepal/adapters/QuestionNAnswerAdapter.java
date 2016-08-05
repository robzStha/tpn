package com.official.trialpassnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.AnswerObject;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.view.TextViewTypeFaced;

import java.util.ArrayList;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class QuestionNAnswerAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<QuestionObject> alQuestionObj;

    public QuestionNAnswerAdapter(Context context, ArrayList<QuestionObject> alTests) {
        this.alQuestionObj = alTests;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alQuestionObj.size();
    }

    @Override
    public Object getItem(int i) {
        return alQuestionObj.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alQuestionObj.get(i).getQid();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.item_question_answer, viewGroup, false);
        }
        ((TextViewTypeFaced)view.findViewById(R.id.tv_question)).setText(alQuestionObj.get(pos).getQuestion());

        DbQuestionAnswer dbQuestionAnswer = new DbQuestionAnswer(context);
        ArrayList<AnswerObject> alAnswerObj = dbQuestionAnswer.getQuestionOptions(alQuestionObj.get(pos).getQid());

        TextViewTypeFaced btn1, btn2, btn3, btn4;
        btn1 = ((TextViewTypeFaced)view.findViewById(R.id.tv_ans1));
        btn1.setTag(alAnswerObj.get(0).getOptionsid());
        btn1.setText(alAnswerObj.get(0).getAnswer());
        btn2 = ((TextViewTypeFaced)view.findViewById(R.id.tv_ans2));
        btn2.setText(alAnswerObj.get(1).getAnswer());
        btn2.setTag(alAnswerObj.get(1).getOptionsid());
        btn3 = ((TextViewTypeFaced)view.findViewById(R.id.tv_ans3));
        btn3.setText(alAnswerObj.get(2).getAnswer());
        btn3.setTag(alAnswerObj.get(2).getOptionsid());
        btn4 = ((TextViewTypeFaced)view.findViewById(R.id.tv_ans4));
        btn4.setText(alAnswerObj.get(3).getAnswer());
        btn4.setTag(alAnswerObj.get(3).getOptionsid());
        return view;
    }

    public void resetAdapter(ArrayList<QuestionObject> tests) {
        alQuestionObj = new ArrayList<>();
        alQuestionObj = tests;
        notifyDataSetChanged();
    }
}
