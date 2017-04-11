package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wehealth.pdqbook.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.getway.repertory.CancerDataConfigure;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.view.ColorArcProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RiskResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RiskResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiskResultFragment extends BaseFragment implements View.OnClickListener {
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
        TextView moreInfo = (TextView) view.findViewById(R.id.risk_result_more_info);
        moreInfo.setOnClickListener(this);
        Button backBtn = (Button) view.findViewById(R.id.risk_result_back);
        backBtn.setOnClickListener(this);
        Button appointmentBtn = (Button) view.findViewById(R.id.risk_result_order);
        appointmentBtn.setOnClickListener(this);
    }

    private void getMoreInfo(CancerDataConfigure data) {
        String url = String.valueOf(data.getIndex());
        String urlType = Strings.IntentActionUrlType.cancerPage.toString();
        String title = getResources().getString(data.getTextResource());
        String uri = Strings.getIntentUri(RiskResultFragment.class.getSimpleName(),
                Strings.INTNET_CONTENT_URL, url,
                Strings.INTENT_ACTION_TYPE, urlType,
                Strings.INTENT_TITLE, title);
        onButtonPressed(Uri.parse(uri));
    }

    private void intentToAppointment() {
        String url = HttpConfigure.getAppointmentUrl();
        String urlType = Strings.IntentActionUrlType.appointment.toString();
        String title =getResources().getString(R.string.order_health_examination);
        String uri = Strings.getIntentUri(RiskResultFragment.class.getSimpleName(),
                Strings.INTNET_CONTENT_URL, url,
                Strings.INTENT_ACTION_TYPE, urlType,
                Strings.INTENT_TITLE, title);
        onButtonPressed(Uri.parse(uri));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.risk_result_more_info:
                getMoreInfo(CancerDataConfigure.Fei);
                break;
            case R.id.risk_result_back:
                ((PDQActivity) getActivity()).clickBack();
                break;
            case R.id.risk_result_order:
                intentToAppointment();
                break;
        }
    }
}
