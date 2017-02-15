package com.wehealth.pdqbook.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wehealth.pdqbook.tool.WebJavascriptInterface;
import com.wehealth.pdqbook.tool.WebSectionJavascriptInterface;
import com.wehealth.pdqbook.tool.PDQWebClient;
import com.wehealth.pdqbook.view.CircleProgressBar;

/**
 * Created by xiaoyang on 2017/1/22.
 */

public class BaseFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("JavascriptInterface")
    protected void initWebView(WebView webView, CircleProgressBar bar, String url, WebJavascriptInterface javascriptInterface) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        webView.addJavascriptInterface(javascriptInterface, "android");
        webView.setWebChromeClient(new PDQWebClient(bar));
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        webView.loadUrl(url);
    }
}
