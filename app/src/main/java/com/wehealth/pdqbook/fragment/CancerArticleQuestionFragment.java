package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wehealth.pdqbook.BuildConfig;
import com.wehealth.pdqbook.activity.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.tool.WebListJavascriptInterface;
import com.wehealth.pdqbook.view.CircleProgressBar;

/**
 *
 */
public class CancerArticleQuestionFragment extends BaseFragment {
    private static final String URL = "url";
    private static final String TITLE = "title";

    private static final String LANGUAGE_EN = "en";
    private static final String LANGUAGE_CN = "cn";

    private String mUrl;
    private String mTitle;

    private boolean isMenuShowing = false;

    private WebView _webView;

    private OnFragmentInteractionListener mListener;

    public CancerArticleQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter.
     * @return A new instance of fragment CancerArticleQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancerArticleQuestionFragment newInstance(String url, String title) {
        CancerArticleQuestionFragment fragment = new CancerArticleQuestionFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancer_article_question, container, false);
        initTitle(view, mTitle);
        _webView = (WebView) view.findViewById(R.id.article_question_wv);
        CircleProgressBar circleProgressBar = (CircleProgressBar) view.findViewById(R.id.article_question_wv_progressbar);
        if (BuildConfig.DEBUG) {
            Log.d("url", mUrl);
        }
        initWebView(_webView, circleProgressBar, mUrl, new WebListJavascriptInterface() {
            @Override
            public void WebDocListDidSelected(String json) {

            }
        });
        return view;
    }

    private void initTitle(View view, String mTitle) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((PDQActivity) getActivity()).setSupportActionBar(toolbar);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) view.findViewById(R.id.tool_bar_title);
        title.setText(mTitle);
        ImageView img = (ImageView) view.findViewById(R.id.toolbar_popwindow);
        img.setVisibility(View.VISIBLE);
        final ImageView languageBtn = (ImageView) view.findViewById(R.id.article_question_change_language);
        languageBtn.setImageResource(R.mipmap.change_language_to_en);
        languageBtn.setTag(LANGUAGE_CN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PDQActivity) getActivity()).clickBack();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMenuShowing) {
                    isMenuShowing = false;
                    _webView.loadUrl("javascript:PagePopover.showSelfMeau()");
                } else {
                    isMenuShowing = true;
                    _webView.loadUrl("javascript:PagePopover.HideSelfMeau()");
                }
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((String)languageBtn.getTag()).equalsIgnoreCase(LANGUAGE_CN)) {
                    languageBtn.setTag(LANGUAGE_EN);
                    languageBtn.setImageResource(R.mipmap.change_language_to_cn);
                } else {
                    languageBtn.setTag(LANGUAGE_CN);
                    languageBtn.setImageResource(R.mipmap.change_language_to_en);
                }
                _webView.loadUrl("javascript:slideLanguage()");
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        if (_webView != null) {
            _webView.stopLoading();
            _webView.destroy();
            _webView = null;
        }
    }
}
