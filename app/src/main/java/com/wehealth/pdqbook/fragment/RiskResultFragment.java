package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.view.ColorArcProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RiskResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RiskResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiskResultFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SCORE = "score";

    // TODO: Rename and change types of parameters
    private String mScore;

    private OnFragmentInteractionListener mListener;

    public RiskResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param score Parameter 1.
     * @return A new instance of fragment RiskResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RiskResultFragment newInstance(String score) {
        RiskResultFragment fragment = new RiskResultFragment();
        Bundle args = new Bundle();
        args.putString(SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScore = getArguments().getString(SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_risk_result, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getString(SCORE);
        }
    }

    private void initView(View view) {
        ColorArcProgressBar bar = (ColorArcProgressBar) view.findViewById(R.id.score_progress_bar);
        bar.setCurrentValues(Float.parseFloat(mScore) * 10);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SCORE, mScore);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
}
