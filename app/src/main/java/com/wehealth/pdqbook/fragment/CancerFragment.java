package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wehealth.pdqbook.BuildConfig;
import com.wehealth.pdqbook.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.adapter.FragPagerAdapter;
import com.wehealth.pdqbook.adapter.ViewPagerAdapter;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.view.CircleProgressBar;

import java.util.ArrayList;

/**
 *
 */
public class CancerFragment extends BaseFragment {
    private static final String TAG = CancerFragment.class.getSimpleName();
    private static final String WEB_URL_INDEX = "url_index";
    private static final String TITLE = "title";

    private String mUrlIndex;
    private String mTitle;

    private String mPreventionUrl, mScreeningUrl, mTreatmentUrl;

    private TabLayout _tabLayout;
    private ViewPager _viewPager;

    private OnFragmentInteractionListener mListener;

    public CancerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param urlIndex url index.
     * @param title page title
     * @return A new instance of fragment CancerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancerFragment newInstance(String urlIndex, String title) {
        CancerFragment fragment = new CancerFragment();
        Bundle args = new Bundle();
        args.putString(WEB_URL_INDEX, urlIndex);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrlIndex = getArguments().getString(WEB_URL_INDEX);
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUrlIndex = savedInstanceState.getString(WEB_URL_INDEX);
            mTitle = savedInstanceState.getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initTitle(view, mTitle);

        _tabLayout = (TabLayout) view.findViewById(R.id.webpage_tab);
        _viewPager = (ViewPager) view.findViewById(R.id.webpage_viewpager);
        initViewPager();
    }

    private void initTitle(View view, String mTitle) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((PDQActivity) getActivity()).setSupportActionBar(toolbar);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) view.findViewById(R.id.tool_bar_title);
        title.setText(mTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PDQActivity) getActivity()).clickBack();
            }
        });
    }

    private void initViewPager() {
        //add tab
        _tabLayout.addTab(_tabLayout.newTab().setText(getString(R.string.prevention)));
        _tabLayout.addTab(_tabLayout.newTab().setText(getString(R.string.screening)));
        _tabLayout.addTab(_tabLayout.newTab().setText(getString(R.string.treatment)));

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(CancerPreventionFragment.newInstance(getPreventionUrl()));
        fragments.add(CancerScreeningFragment.newInstance(getScreeningUrl()));
        fragments.add(CancerTreatmentFragment.newInstance(getTreatmentUrl()));

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.prevention));
        titles.add(getString(R.string.screening));
        titles.add(getString(R.string.treatment));

        FragPagerAdapter pagerAdapter = new FragPagerAdapter(getChildFragmentManager(), fragments, titles);
        _viewPager.setAdapter(pagerAdapter);

        _tabLayout.setupWithViewPager(_viewPager);
    }

    private String getPreventionUrl() {
        if (mPreventionUrl == null) {
            mPreventionUrl = String.format(HttpConfigure.getUrlCancerWeb(mUrlIndex),
                    HttpConfigure.param_cancerpage_index_prevention);
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "prevention : " + mPreventionUrl);
        }
        return mPreventionUrl;
    }

    private String getScreeningUrl() {
        if (mScreeningUrl == null) {
            mScreeningUrl = String.format(HttpConfigure.getUrlCancerWeb(mUrlIndex),
                    HttpConfigure.param_cancerpage_index_screening);
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "screening : " + mScreeningUrl);
        }
        return mScreeningUrl;
    }

    private String getTreatmentUrl() {
        if (mTreatmentUrl == null) {
            mTreatmentUrl = String.format(HttpConfigure.getUrlCancerWeb(mUrlIndex),
                    HttpConfigure.param_cancerpage_index_treatment);
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "treatment : " + mTreatmentUrl);
        }
        return mTreatmentUrl;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(WEB_URL_INDEX, mUrlIndex);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
