package com.wehealth.pdqbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.wehealth.pdqbook.adapter.ViewPagerAdapter;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.tool.PDQWebClient;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.view.CircleProgressBar;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2016/12/9.
 */

public class WebPageActivity extends AppCompatActivity {

    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private WebView _searchView;

    private String _url;
    private String _titleStr;
    private String _type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        _url = getIntent().getStringExtra(Strings.INTNET_CONTENT_URL);
        Log.d("web url", _url);
        _type = getIntent().getStringExtra(Strings.INTENT_ACTION_TYPE);
        _titleStr = getIntent().getStringExtra(Strings.INTENT_TITLE);

        initToolBar();

        CircleProgressBar progressBar = (CircleProgressBar) findViewById(R.id.webpage_progressbar);
        _tabLayout = (TabLayout) findViewById(R.id.webpage_tab);
        _viewPager = (ViewPager) findViewById(R.id.webpage_viewpager);
        _searchView = (WebView) findViewById(R.id.webpage_webview);

        if (_type.equals(Strings.IntentActionUrlType.cancerPage.toString())) {
            progressBar.setVisibility(View.GONE);
            initViewpager();
        } else {
            _tabLayout.setVisibility(View.GONE);
            _viewPager.setVisibility(View.GONE);
            _searchView.setVisibility(View.VISIBLE);
            initWebView(_searchView, progressBar, _url);
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        TextView title = (TextView) findViewById(R.id.bar_title);
        title.setText(_titleStr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView doneAction = (TextView) findViewById(R.id.tool_bar_done);
        if (!_type.equals(Strings.IntentActionUrlType.cancerPage.toString())) {
            doneAction.setVisibility(View.VISIBLE);
        } else {
            doneAction.setVisibility(View.GONE);
        }
        doneAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initViewpager() {
        _tabLayout.setVisibility(View.VISIBLE);
        _viewPager.setVisibility(View.VISIBLE);
        _searchView.setVisibility(View.GONE);

        ArrayList<View> views = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        View preventionView = LayoutInflater.from(this).inflate(R.layout.item_viewpager_prevention, null);
        WebView webView1 = (WebView) preventionView.findViewById(R.id.webpager_prevention);
        CircleProgressBar bar1 = (CircleProgressBar) preventionView.findViewById(R.id.webpage_prevention_progressbar);
        initWebView(webView1, bar1, String.format(_url, HttpConfigure.param_cancerpage_index_prevention));
        views.add(preventionView);
        String prevention = getString(R.string.prevention);
        titles.add(prevention);
        _tabLayout.addTab(_tabLayout.newTab().setText(prevention));

        View screeningView = LayoutInflater.from(this).inflate(R.layout.item_viewpager_screening, null);
        WebView webView2 = (WebView) screeningView.findViewById(R.id.webpager_screening);
        CircleProgressBar bar2 = (CircleProgressBar) screeningView.findViewById(R.id.webpager_screening_progressbar);
        initWebView(webView2, bar2, String.format(_url, HttpConfigure.param_cancerpage_index_screening));
        views.add(screeningView);
        String screening = getString(R.string.screening);
        titles.add(screening);
        _tabLayout.addTab(_tabLayout.newTab().setText(screening));

        View treatmentView = LayoutInflater.from(this).inflate(R.layout.item_viewpager_treatment, null);
        WebView webView3 = (WebView) treatmentView.findViewById(R.id.webpager_treatment);
        CircleProgressBar bar3 = (CircleProgressBar) treatmentView.findViewById(R.id.webpage_treatment_progressbar);
        initWebView(webView3, bar3, String.format(_url, HttpConfigure.param_cancerpage_index_treatment));
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
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new PDQWebClient(bar));
        webView.loadUrl(url);

    }

}
