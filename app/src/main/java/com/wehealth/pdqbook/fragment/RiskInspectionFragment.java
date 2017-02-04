package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * {@link RiskInspectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RiskInspectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiskInspectionFragment extends BaseFragment {
    private static final String WEB_URL = "url";

    private String mUrl;

    private OnFragmentInteractionListener mListener;

    public RiskInspectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment RiskInspectionFragment.
     */
    public static RiskInspectionFragment newInstance(String url) {
        RiskInspectionFragment fragment = new RiskInspectionFragment();
        Bundle args = new Bundle();
        args.putString(WEB_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(WEB_URL);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(WEB_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_risk_inspection, container, false);
        WebView webView = (WebView) view.findViewById(R.id.risk_webview);
        CircleProgressBar bar = (CircleProgressBar) view.findViewById(R.id.risk_progressbar);
        initWebView(webView, bar, mUrl);
        return view;
    }

    private void initWebView(WebView webView, CircleProgressBar bar, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new PBQWebClient(bar));
        webView.loadUrl(url);
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
        outState.putString(WEB_URL, mUrl);
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
