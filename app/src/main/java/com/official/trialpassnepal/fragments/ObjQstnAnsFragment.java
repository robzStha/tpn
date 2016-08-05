package com.official.trialpassnepal.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.official.trialpassnepal.ObjQstnAns;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObjQstnAnsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjQstnAnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjQstnAnsFragment extends Fragment {
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
    private ButtonTypeFaced btnNext;
    private ViewPager viewPager;

    public ObjQstnAnsFragment() {
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
    public static ObjQstnAnsFragment newInstance(QuestionObject questionObject, int position, int size) {
        ObjQstnAnsFragment fragment = new ObjQstnAnsFragment();
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
        btnNext = (ButtonTypeFaced) view.findViewById(R.id.bnt_next_qst);
        btnNext.setVisibility(View.GONE);

        ivSign = (ImageView) view.findViewById(R.id.iv_signs);
        String imgLoc = mQuestionObj.getImgLoc();
        if (imgLoc != null && !imgLoc.isEmpty()) {
            ivSign.setVisibility(View.VISIBLE);
            Picasso.with(activity).load(CommonDef.HOME_URL + "/images/" + imgLoc).into(ivSign);
        } else {
            ivSign.setVisibility(View.GONE);
        }

        tvAns1 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans1));
        tvAns1.setText(alAnswerObj.get(0).getAnswer());
        tvAns2 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans2));
        tvAns2.setText(alAnswerObj.get(1).getAnswer());
        tvAns3 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans3));
        tvAns3.setText(alAnswerObj.get(2).getAnswer());
        tvAns4 = ((TextViewTypeFaced) view.findViewById(R.id.tv_ans4));
        tvAns4.setText(alAnswerObj.get(3).getAnswer());
        if(alAnswerObj.get(0).isCorrectAnswer()){
            tvAns1.setBackground(getResources().getDrawable(R.drawable.bg_btn_green));
        }
        if(alAnswerObj.get(1).isCorrectAnswer()){
            tvAns2.setBackground(getResources().getDrawable(R.drawable.bg_btn_green));
        }
        if(alAnswerObj.get(2).isCorrectAnswer()){
            tvAns3.setBackground(getResources().getDrawable(R.drawable.bg_btn_green));
        }
        if(alAnswerObj.get(3).isCorrectAnswer()){
            tvAns4.setBackground(getResources().getDrawable(R.drawable.bg_btn_green));
        }

        return view;
    }

    private ObjQstnAns activity;
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bnt_next_qst:
                    break;
            }
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
        activity = (ObjQstnAns) context;
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
