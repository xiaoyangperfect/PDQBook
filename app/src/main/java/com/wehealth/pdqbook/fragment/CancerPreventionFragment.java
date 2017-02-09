package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.tool.PBQWebClient;
import com.wehealth.pdqbook.view.CircleProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CancerPreventionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CancerPreventionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancerPreventionFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARAM_URL = "url";

    private String mUrl;

    private View mView;
    private WebView mWebView;


    private OnFragmentInteractionListener mListener;

    public CancerPreventionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment CancerPreventionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancerPreventionFragment newInstance(String url) {
        CancerPreventionFragment fragment = new CancerPreventionFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(PARAM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cancer_prevention, container, false);
            mWebView = (WebView) mView.findViewById(R.id.webpager_prevention);
            CircleProgressBar bar1 = (CircleProgressBar) mView.findViewById(R.id.webpage_prevention_progressbar);
            initWebView(mWebView, bar1, mUrl);
        }
        return mView;
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

    private void initWebView(WebView webView, CircleProgressBar bar, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new PBQWebClient(bar));
        webView.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.destroy();
        mWebView = null;
    }
}
