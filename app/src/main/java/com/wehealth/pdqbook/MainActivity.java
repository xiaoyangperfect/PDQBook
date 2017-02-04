package com.wehealth.pdqbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.tool.Strings;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

//        ImageView shidaoBtn = (ImageView) findViewById(R.id.btn_shidaoai);
//        ImageView ruxianBtn = (ImageView) findViewById(R.id.btn_ruxianai);
//        ImageView ganBtn = (ImageView) findViewById(R.id.btn_ganai);
//        ImageView zhichangBtn = (ImageView) findViewById(R.id.btn_zhichangai);
//        ImageView feiBtn = (ImageView) findViewById(R.id.btn_feiai);
//        ImageView weiBtn = (ImageView) findViewById(R.id.btn_weiai);
//        ImageView naoBtn = (ImageView) findViewById(R.id.btn_naoai);
//
//        RelativeLayout searchLayout = (RelativeLayout) findViewById(R.id.mainpage_search_layout);
//        RelativeLayout aboutLayout = (RelativeLayout) findViewById(R.id.mainpage_about_layout);
//
//        shidaoBtn.setOnClickListener(this);
//        ruxianBtn.setOnClickListener(this);
//        ganBtn.setOnClickListener(this);
//        zhichangBtn.setOnClickListener(this);
//        feiBtn.setOnClickListener(this);
//        weiBtn.setOnClickListener(this);
//        naoBtn.setOnClickListener(this);
//        searchLayout.setOnClickListener(this);
//        aboutLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent();
//        intent.setClass(this, WebPageActivity.class);
//        String url = "";
//        switch (view.getId()) {
//            case R.id.btn_shidaoai:
//                url = HttpConfigure.getUrlCancerWeb( 1 + "");
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.shidaoai));
//                break;
//            case R.id.btn_ruxianai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 6 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.ruxianai));
//                break;
//            case R.id.btn_ganai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 2 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.ganai));
//                break;
//            case R.id.btn_zhichangai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 3 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.zhichangai));
//                break;
//            case R.id.btn_feiai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 5 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.feiai));
//                break;
//            case R.id.btn_weiai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 4 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.weiai));
//                break;
//            case R.id.btn_naoai:
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.cancerPage.toString());
//                url = HttpConfigure.getUrlCancerWeb( 7 + "");
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.naoai));
//                break;
//            case R.id.mainpage_search_layout:
//
//                break;
//            case R.id.mainpage_about_layout:
//                url = HttpConfigure.getUrlRiskInspection();
//                intent.putExtra(Strings.INTNET_URL_TYPE, Strings.WebPageUrlType.riskInspection.toString());
//                intent.putExtra(Strings.INTENT_TITLE, getString(R.string.risk_inspection));
//                break;
//        }
//        intent.putExtra(Strings.INTNET_CONTENT_URL, url);
//        startActivity(intent);
    }
}
