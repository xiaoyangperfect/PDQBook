package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wehealth.pdqbook.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.tool.Strings;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        ((PDQActivity) getActivity()).hideToolbar();
        return view;
    }

    private void initView(View view) {

        ImageView shidaoBtn = (ImageView) view.findViewById(R.id.btn_shidaoai);
        ImageView ruxianBtn = (ImageView) view.findViewById(R.id.btn_ruxianai);
        ImageView ganBtn = (ImageView) view.findViewById(R.id.btn_ganai);
        ImageView zhichangBtn = (ImageView) view.findViewById(R.id.btn_zhichangai);
        ImageView feiBtn = (ImageView) view.findViewById(R.id.btn_feiai);
        ImageView weiBtn = (ImageView) view.findViewById(R.id.btn_weiai);
        ImageView naoBtn = (ImageView) view.findViewById(R.id.btn_naoai);

        RelativeLayout searchLayout = (RelativeLayout) view.findViewById(R.id.mainpage_search_layout);
        RelativeLayout aboutLayout = (RelativeLayout) view.findViewById(R.id.mainpage_about_layout);

        shidaoBtn.setOnClickListener(this);
        ruxianBtn.setOnClickListener(this);
        ganBtn.setOnClickListener(this);
        zhichangBtn.setOnClickListener(this);
        feiBtn.setOnClickListener(this);
        weiBtn.setOnClickListener(this);
        naoBtn.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onClick(View v) {
        String url = "";
        String urlType = "";
        String title = "";
        switch (v.getId()) {
            case R.id.btn_shidaoai:
                url = String.valueOf(1);
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                title = getString(R.string.shidaoai);
                break;
            case R.id.btn_ruxianai:
                urlType =  Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(6);
                title = getString(R.string.ruxianai);
                break;
            case R.id.btn_ganai:
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(2);
                title = getString(R.string.ganai);
                break;
            case R.id.btn_zhichangai:
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(3);
                title = getString(R.string.zhichangai);
                break;
            case R.id.btn_feiai:
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(5);
                title = getString(R.string.feiai);
                break;
            case R.id.btn_weiai:
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(4);
                title = getString(R.string.weiai);
                break;
            case R.id.btn_naoai:
                urlType = Strings.WebPageUrlType.cancerPage.toString();
                url = String.valueOf(7);
                title = getString(R.string.naoai);
                break;
            case R.id.mainpage_search_layout:

                break;
            case R.id.mainpage_about_layout:
                url = "";
                urlType = Strings.WebPageUrlType.riskInspection.toString();
                title = getString(R.string.risk_inspection);
                break;
        }
        String uri = Strings.getIntentUri(MainFragment.class.getSimpleName(),
                Strings.INTNET_CONTENT_URL, url,
                Strings.INTNET_URL_TYPE, urlType,
                Strings.INTENT_TITLE, title);

        onButtonPressed(Uri.parse(uri));
    }
}
