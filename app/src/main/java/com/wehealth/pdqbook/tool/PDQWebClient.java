package com.wehealth.pdqbook.tool;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.wehealth.pdqbook.view.CircleProgressBar;

/**
 * Created by xiaoyang on 2017/1/29.
 */

public class PDQWebClient extends WebChromeClient {

    private CircleProgressBar progressBar;

    public PDQWebClient(CircleProgressBar bar) {
        this.progressBar = bar;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 1000);
        } else {
            progressBar.setProgress(newProgress);
        }
    }



    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        progressBar.setVisibility(View.VISIBLE);
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}