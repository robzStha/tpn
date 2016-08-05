package com.official.trialpassnepal.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.AnswerObject;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.MockTestViewPager;
import com.official.trialpassnepal.view.TextViewTypeFaced;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MockTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MockTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MockTestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION_OBJ = "param1";
    private static final String ARG_POS = "position";
    private static final String ARG_SIZE = "size";
    private int selectedAnswerId;
    private boolean isCorrect;
    private TextViewTypeFaced btnChecked = null;
    // TODO: Rename and change types of parameters
    private QuestionObject mQuestionObj;
    private int mPosition;
    private int mSize;
    private ImageView ivSign;

    private OnFragmentInteractionListener mListener;
    private ButtonTypeFaced btnNext, btnPrev;
    private ViewPager viewPager;
    private PopupWindow mpopup;
    private ViewGroup.LayoutParams params;

    public MockTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionObject Parameter 1.
     * @return A new instance of fragment MockTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MockTestFragment newInstance(QuestionObject questionObject, int position, int size) {
        MockTestFragment fragment = new MockTestFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION_OBJ, questionObject);
        args.putInt(ARG_POS, position);
        args.putInt(ARG_SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionObj = (QuestionObject) getArguments().getSerializable(ARG_QUESTION_OBJ);
            mPosition = getArguments().getInt(ARG_POS);
            mSize = getArguments().getInt(ARG_SIZE);
            mQuestionObj.getQid();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewPager = (ViewPager) container;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mock_test, container, false);
        DbQuestionAnswer dbQuestionAnswer = new DbQuestionAnswer(getContext());

        ArrayList<AnswerObject> alAnswerObj = dbQuestionAnswer.getQuestionOptions(mQuestionObj.getQid());

        TextViewTypeFaced tvAns1, tvAns2, tvAns3, tvAns4, tvQuestion;
        tvQuestion = ((TextViewTypeFaced) view.findViewById(R.id.tv_question));
        tvQuestion.setTag(mQuestionObj.getQid());
        tvQuestion.setText(mQuestionObj.getQuestion());

        ivSign = (ImageView) view.findViewById(R.id.iv_signs);
        String imgLoc = mQuestionObj.getImgLoc();
        if (imgLoc != null && !imgLoc.isEmpty()) {
            ivSign.setVisibility(View.VISIBLE);
            Picasso.with(activity).load(CommonDef.HOME_URL + "/images/" + imgLoc).into(ivSign);
        } else {
            ivSign.setVisibility(View.GONE);
        }


        tvAns1 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans1));
        tvAns1.setTag(alAnswerObj.get(0));
        tvAns1.setText(alAnswerObj.get(0).getAnswer());
        tvAns2 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans2));
        tvAns2.setText(alAnswerObj.get(1).getAnswer());
        tvAns2.setTag(alAnswerObj.get(1));
        tvAns3 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans3));
        tvAns3.setText(alAnswerObj.get(2).getAnswer());
        tvAns3.setTag(alAnswerObj.get(2));
        tvAns4 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans4));
        tvAns4.setText(alAnswerObj.get(3).getAnswer());
        tvAns4.setTag(alAnswerObj.get(3));
        tvAns1.setOnClickListener(tvClickListener);
        tvAns2.setOnClickListener(tvClickListener);
        tvAns3.setOnClickListener(tvClickListener);
        tvAns4.setOnClickListener(tvClickListener);
        btnNext = (ButtonTypeFaced) view.findViewById(R.id.bnt_next_qst);
        btnPrev = (ButtonTypeFaced) view.findViewById(R.id.bnt_prev_qst);

        if(mPosition == 0){
            btnPrev.setVisibility(View.GONE);
        }else{
            btnPrev.setVisibility(View.VISIBLE);
        }

        if (mPosition == -1) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
        btnNext.setOnClickListener(btnClickListener);

        return view;
    }

    private MockTestViewPager activity;
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bnt_next_qst:
                    btnNextClick();
                    break;
            }
        }
    };

    private void btnPrevClick(){
        if(viewPager.getCurrentItem()!=0){
            viewPager.setCurrentItem((mPosition - 1), true);
        }
    }

    private void btnNextClick() {
        if (selectedAnswerId != 0) {
            viewPager.setCurrentItem((mPosition + 1), true);
            HashMap<Integer, Boolean> hmSelectedAns = new HashMap<>();
            hmSelectedAns.put(selectedAnswerId, isCorrect);
            activity.hmAnswers.put(mQuestionObj.getQid(), hmSelectedAns);
            System.out.print("Hashmap for: " + activity.hmAnswers.toString());
            if ((mSize-1) == mPosition) {
                getCorrectAnswers();
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.app_name);
            builder.setMessage("Please select an option.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
//                System.out.println("Answer not selected");
        }
    }

    // Shows number of correct answers given.
    private void getCorrectAnswers() {
        int correctAnsCount = 0;
        int count = activity.hmAnswers.size();
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.final_answer_list, null, false);

        mpopup = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(view, Gravity.CENTER, 0, 0); // Displaying popup


        Integer key;
        Iterator myVeryOwnIterator = activity.hmAnswers.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            key=(Integer)myVeryOwnIterator.next();
            HashMap<Integer, Boolean> answer = activity.hmAnswers.get(key);
            Iterator answerIterator = answer.keySet().iterator();
            Integer ansKey;
            while(answerIterator.hasNext()){
                ansKey = (Integer) answerIterator.next();
                Boolean value = answer.get(ansKey);
                if(value){
                    correctAnsCount++;
                }
            }
        }
        String msg;
        if(count == correctAnsCount){
            msg = "Congratulations you have given all answers correctly.";
        }else
            msg = "You have given "+ correctAnsCount+ " answers correct out of "+ count+ " questions";

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setTitle(getString(R.string.app_name));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                activity.finish();
            }
        });
        builder.create().show();
    }

    private View.OnClickListener tvClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextViewTypeFaced btnCurrent = (TextViewTypeFaced) v;
            if (btnChecked != null && btnCurrent != btnChecked) {
                btnChecked.setBackground(getResources().getDrawable(R.drawable.bg_btn_blue));
            }
            btnCurrent.setBackground(getResources().getDrawable(R.drawable.bg_btn_green));
            btnChecked = btnCurrent;
            AnswerObject answerObject = (AnswerObject) btnCurrent.getTag();
            selectedAnswerId = answerObject.getOptionsid();
            isCorrect = answerObject.isCorrectAnswer();
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MockTestViewPager) context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
