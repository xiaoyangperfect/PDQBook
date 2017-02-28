package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wehealth.pdqbook.BuildConfig;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.model.ArticleIntent;
import com.wehealth.pdqbook.tool.PDQWebClient;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.tool.WebListJavascriptInterface;
import com.wehealth.pdqbook.view.CircleProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CancerScreeningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CancerScreeningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancerScreeningFragment extends BaseFragment {
    private static final String TAG = CancerScreeningFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARAM_URL = "url";

    private String mUrl;

    private WebView mWebView;
    private View mView;

    private OnFragmentInteractionListener mListener;

    public CancerScreeningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment CancerScreeningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancerScreeningFragment newInstance(String url) {
        CancerScreeningFragment fragment = new CancerScreeningFragment();
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
            mView = inflater.inflate(R.layout.fragment_cancer_screening, container, false);
            mWebView = (WebView) mView.findViewById(R.id.webpager_screening);
            CircleProgressBar bar1 = (CircleProgressBar) mView.findViewById(R.id.webpager_screening_progressbar);
            initWebView(mWebView, bar1, mUrl, new WebListJavascriptInterface() {
                @JavascriptInterface
                @Override
                public void WebDocListDidSelected(String json) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "WebDocListDidSelected: " + json);
                    }
                    ArticleIntent art = new ArticleIntent().parser(json);
                    if (art == null)
                        return;
                    final String uri = Strings.getIntentUri(MainFragment.class.getSimpleName(),
                            Strings.INTNET_CONTENT_URL, art.getUrl(),
                            Strings.INTENT_ACTION_TYPE, Strings.IntentActionUrlType.cancerArticleQuestion.toString(),
                            Strings.INTENT_TITLE, art.getTitle());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onButtonPressed(Uri.parse(uri));
                        }
                    });
                }
            });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.stopLoading();
        mWebView.destroy();
        mWebView = null;
    }
}
