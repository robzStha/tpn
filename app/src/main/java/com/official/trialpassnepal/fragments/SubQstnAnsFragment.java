package com.official.trialpassnepal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.official.trialpassnepal.R;
import com.official.trialpassnepal.SubQstnAns;
import com.official.trialpassnepal.db.DbQuestionAnswer;
import com.official.trialpassnepal.objects.QuestionObject;
import com.official.trialpassnepal.objects.SubQstnAnsObject;
import com.official.trialpassnepal.utils.CommonDef;
import com.official.trialpassnepal.view.ButtonTypeFaced;
import com.official.trialpassnepal.view.MockTestViewPager;
import com.official.trialpassnepal.view.TextViewTypeFaced;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubQstnAnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubQstnAnsFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUB_QSTN_ANS_OBJ = "param1";
    private static final String ARG_POS = "position";
    private static final String ARG_SIZE = "size";
    private int selectedAnswerId;
    private boolean isCorrect;
    private TextViewTypeFaced btnChecked = null;
    // TODO: Rename and change types of parameters
    private SubQstnAnsObject subQstnAnsObject;
    private int mPosition;
    private int mSize;
    private ImageView ivSign;

    private OnFragmentInteractionListener mListener;
    private ButtonTypeFaced btnNext;
    private ViewPager viewPager;

    public SubQstnAnsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subQstnAnsObject Parameter 1.
     * @return A new instance of fragment MockTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubQstnAnsFragment newInstance(SubQstnAnsObject subQstnAnsObject, int position, int size) {
        SubQstnAnsFragment fragment = new SubQstnAnsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SUB_QSTN_ANS_OBJ, subQstnAnsObject);
        args.putInt(ARG_POS, position);
        args.putInt(ARG_SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subQstnAnsObject = (SubQstnAnsObject) getArguments().getSerializable(ARG_SUB_QSTN_ANS_OBJ);
            mPosition = getArguments().getInt(ARG_POS);
            mSize = getArguments().getInt(ARG_SIZE);
            subQstnAnsObject.getQid();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewPager = (ViewPager) container;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_qstn_ans, container, false);
        DbQuestionAnswer dbQuestionAnswer = new DbQuestionAnswer(getContext());

        TextViewTypeFaced tvQuestion, tvAnswer;
        tvQuestion = ((TextViewTypeFaced) view.findViewById(R.id.tv_question));
        tvAnswer = ((TextViewTypeFaced) view.findViewById(R.id.tv_answer));
        tvQuestion.setTag(subQstnAnsObject.getQid());
        tvQuestion.setText(subQstnAnsObject.getQuestion());
        tvAnswer.setText(subQstnAnsObject.getAnswer());

        return view;
    }

    private SubQstnAns activity;
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
        activity = (SubQstnAns) context;
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
