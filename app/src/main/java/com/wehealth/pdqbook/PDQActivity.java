package com.wehealth.pdqbook;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wehealth.pdqbook.fragment.BaseFragment;
import com.wehealth.pdqbook.fragment.CancerFragment;
import com.wehealth.pdqbook.fragment.MainFragment;
import com.wehealth.pdqbook.fragment.RiskInspectionFragment;
import com.wehealth.pdqbook.fragment.SearchFragment;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.getway.datamodel.SearchRecord;
import com.wehealth.pdqbook.getway.repertory.db.IRecordReceiver;
import com.wehealth.pdqbook.getway.repertory.db.PDQDB;
import com.wehealth.pdqbook.getway.repertory.db.table.SearchRecordTable;
import com.wehealth.pdqbook.tool.Strings;

import java.util.ArrayList;

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
        } else {
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
        String type = uri.getQueryParameter(Strings.INTENT_ACTION_TYPE);
        String urlIndex = uri.getQueryParameter(Strings.INTNET_CONTENT_URL);
        String title = uri.getQueryParameter(Strings.INTENT_TITLE);
        if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerPage.toString())) {
            changeFragment(CancerFragment.newInstance(urlIndex), title);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.riskInspection.toString())){
            changeFragment(RiskInspectionFragment.newInstance(HttpConfigure.getUrlRiskInspection()), title);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.search.toString())) {
            changeFragment(SearchFragment.newInstance(), title);
        }

    }

    public void hideToolbar() {
        _toolbar.setVisibility(View.GONE);
    }

    //get search records
    public ArrayList<SearchRecord> getSearchRecord() {
        return SearchRecord.get();
    }
}
