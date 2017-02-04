package com.wehealth.pdqbook;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wehealth.pdqbook.fragment.BaseFragment;
import com.wehealth.pdqbook.fragment.CancerFragment;
import com.wehealth.pdqbook.fragment.MainFragment;
import com.wehealth.pdqbook.fragment.RiskInspectionFragment;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.tool.Strings;

public class PDQActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    private Toolbar _toolbar;
    private TextView _title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdq);
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _title = (TextView) findViewById(R.id.tool_bar_title);

        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });

        if (findViewById(R.id.fragment_container) != null) {
            BaseFragment mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();
            setTitleView(mainFragment, null);
        }
    }

    private void changeFragment(Fragment fragment, String title) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        setTitleView(fragment, title);
    }

    private void setTitleView(Fragment fragment, String title) {
        if (fragment instanceof  MainFragment) {
            _toolbar.setVisibility(View.GONE);
        } else if (fragment instanceof CancerFragment) {
            _toolbar.setVisibility(View.VISIBLE);
            _title.setText(title);
        } else if (fragment instanceof RiskInspectionFragment) {
            _toolbar.setVisibility(View.VISIBLE);
            _title.setText(title);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String fragment = uri.getFragment();
        if (fragment.equalsIgnoreCase(MainFragment.class.getSimpleName())) {
            onActionMainFragment(uri);
        }
    }

    private void onActionMainFragment(Uri uri) {
        String type = uri.getQueryParameter(Strings.INTNET_URL_TYPE);
        String urlIndex = uri.getQueryParameter(Strings.INTNET_CONTENT_URL);
        String title = uri.getQueryParameter(Strings.INTENT_TITLE);
        if (type.equalsIgnoreCase(Strings.WebPageUrlType.cancerPage.toString())) {
            changeFragment(CancerFragment.newInstance(urlIndex), title);
        } else if (type.equalsIgnoreCase(Strings.WebPageUrlType.riskInspection.toString())){
            changeFragment(RiskInspectionFragment.newInstance(HttpConfigure.getUrlRiskInspection()), title);
        }

    }

    public void hideToolbar() {
        _toolbar.setVisibility(View.GONE);
    }
}
