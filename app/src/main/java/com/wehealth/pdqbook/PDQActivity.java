package com.wehealth.pdqbook;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wehealth.pdqbook.fragment.BaseFragment;
import com.wehealth.pdqbook.fragment.CancerArticleQuestionFragment;
import com.wehealth.pdqbook.fragment.CancerFragment;
import com.wehealth.pdqbook.fragment.MainFragment;
import com.wehealth.pdqbook.fragment.RiskInspectionFragment;
import com.wehealth.pdqbook.fragment.SearchFragment;
import com.wehealth.pdqbook.fragment.SearchResultFragment;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.getway.datamodel.SearchRecord;
import com.wehealth.pdqbook.tool.Strings;

import java.util.ArrayList;

public class PDQActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdq);


        if (findViewById(R.id.fragment_container) != null) {
            BaseFragment mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();
        }
    }

    public void clickBack() {
        getSupportFragmentManager().popBackStack();
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String fragment = uri.getFragment();
        if (fragment.equalsIgnoreCase(MainFragment.class.getSimpleName())) {
            onActionMainFragment(uri);
        } else if (fragment.equalsIgnoreCase(SearchFragment.class.getSimpleName())) {
            String searchContent = uri.getQueryParameter(Strings.INTENT_CONTENT);
            changeFragment(SearchResultFragment.newInstance(getString(R.string.search_result), searchContent));
        } else if (fragment.equalsIgnoreCase(SearchResultFragment.class.getSimpleName())) {
            String url = uri.getQueryParameter(Strings.INTNET_CONTENT_URL).replaceAll("!!!", "#");
            String title = uri.getQueryParameter(Strings.INTENT_TITLE);
            changeFragment(CancerArticleQuestionFragment.newInstance(url, title));
        }
    }

    private void onActionMainFragment(Uri uri) {
        String type = uri.getQueryParameter(Strings.INTENT_ACTION_TYPE);
        String urlIndex = uri.getQueryParameter(Strings.INTNET_CONTENT_URL);
        String title = uri.getQueryParameter(Strings.INTENT_TITLE);
        if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerPage.toString())) {
            changeFragment(CancerFragment.newInstance(urlIndex, title));
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.riskInspection.toString())){
            changeFragment(RiskInspectionFragment.newInstance(HttpConfigure.getUrlRiskInspection(), title));
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.search.toString())) {
            changeFragment(SearchFragment.newInstance(title));
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerArticleQuestion.toString())) {
            String url = urlIndex.replaceAll("!!!", "#");
            changeFragment(CancerArticleQuestionFragment.newInstance(url, title));
        }

    }

    //get search records
    public ArrayList<SearchRecord> getSearchRecord() {
        return SearchRecord.get();
    }
}
