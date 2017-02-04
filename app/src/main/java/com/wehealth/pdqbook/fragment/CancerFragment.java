package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wehealth.pdqbook.BuildConfig;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.WebPageActivity;
import com.wehealth.pdqbook.adapter.ViewPagerAdapter;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.tool.PBQWebClient;
import com.wehealth.pdqbook.view.CircleProgressBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CancerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CancerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancerFragment extends BaseFragment {
    private static final String TAG = CancerFragment.class.getSimpleName();
    private static final String WEB_URL_INDEX = "url_index";

    private String mUrlIndex;

    private String mPreventionUrl, mScreeningUrl, mTreatmentUrl;

    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private WebView webView1, webView2, webView3;

    private OnFragmentInteractionListener mListener;

    public CancerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment CancerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancerFragment newInstance(String title) {
        CancerFragment fragment = new CancerFragment();
        Bundle args = new Bundle();
        args.putString(WEB_URL_INDEX, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrlIndex = getArguments().getString(WEB_URL_INDEX);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUrlIndex = savedInstanceState.getString(WEB_URL_INDEX);
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
        _tabLayout = (TabLayout) view.findViewById(R.id.webpage_tab);
        _viewPager = (ViewPager) view.findViewById(R.id.webpage_viewpager);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<View> views = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        View preventionView = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_prevention, null);
        webView1 = (WebView) preventionView.findViewById(R.id.webpager_prevention);
        CircleProgressBar bar1 = (CircleProgressBar) preventionView.findViewById(R.id.webpage_prevention_progressbar);
        initWebView(webView1, bar1, getPreventionUrl());
        views.add(preventionView);
        String prevention = getString(R.string.prevention);
        titles.add(prevention);
        _tabLayout.addTab(_tabLayout.newTab().setText(prevention));

        View screeningView = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_screening, null);
        webView2 = (WebView) screeningView.findViewById(R.id.webpager_screening);
        CircleProgressBar bar2 = (CircleProgressBar) screeningView.findViewById(R.id.webpager_screening_progressbar);
        initWebView(webView2, bar2, getScreeningUrl());
        views.add(screeningView);
        String screening = getString(R.string.screening);
        titles.add(screening);
        _tabLayout.addTab(_tabLayout.newTab().setText(screening));

        View treatmentView = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_treatment, null);
        webView3 = (WebView) treatmentView.findViewById(R.id.webpager_treatment);
        CircleProgressBar bar3 = (CircleProgressBar) treatmentView.findViewById(R.id.webpage_treatment_progressbar);
        initWebView(webView3, bar3, getTreatmentUrl());
        views.add(treatmentView);
        String treatment = getString(R.string.treatment);
        titles.add(treatment);
        _tabLayout.addTab(_tabLayout.newTab().setText(treatment));

        ViewPagerAdapter adapter = new ViewPagerAdapter(views, titles);
        _viewPager.setAdapter(adapter);
        _tabLayout.setupWithViewPager(_viewPager);
        _tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initWebView(WebView webView, CircleProgressBar bar, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new PBQWebClient(bar));
        webView.loadUrl(url);
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
        if (webView1 != null) {
            webView1.stopLoading();
            webView1.destroy();
            webView1 = null;
        }
        if (webView2 != null) {
            webView2.stopLoading();
            webView2.destroy();
            webView2 = null;
        }
        if (webView3 != null) {
            webView3.stopLoading();
            webView3.destroy();
            webView3 = null;
        }
    }
}
