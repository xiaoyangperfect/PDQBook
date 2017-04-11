package com.wehealth.pdqbook;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wehealth.pdqbook.fragment.BaseFragment;
import com.wehealth.pdqbook.fragment.CancerArticleQuestionFragment;
import com.wehealth.pdqbook.fragment.CancerFragment;
import com.wehealth.pdqbook.fragment.MainFragment;
import com.wehealth.pdqbook.fragment.RiskInspectionFragment;
import com.wehealth.pdqbook.fragment.RiskResultFragment;
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

    private void changeFragment(Fragment fragment, boolean isAddToBackStack) {
        if (isAddToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String fragment = uri.getFragment();
        if (fragment.equalsIgnoreCase(MainFragment.class.getSimpleName())) {
            onActionMainFragment(uri);
        } else if (fragment.equalsIgnoreCase(SearchFragment.class.getSimpleName())) {
            String searchContent = uri.getQueryParameter(Strings.INTENT_CONTENT);
            changeFragment(SearchResultFragment.newInstance(getString(R.string.search_result), searchContent), true);
        } else if (fragment.equalsIgnoreCase(SearchResultFragment.class.getSimpleName())) {
            String url = uri.getQueryParameter(Strings.INTNET_CONTENT_URL).replaceAll("!!!", "#");
            String title = uri.getQueryParameter(Strings.INTENT_TITLE);
            changeFragment(CancerArticleQuestionFragment.newInstance(url, title), true);
        } else if (fragment.equalsIgnoreCase(RiskInspectionFragment.class.getSimpleName())) {
            String content = uri.getQueryParameter(Strings.INTENT_CONTENT);
            changeFragment(RiskResultFragment.newInstance(content), false);
        } else if (fragment.equalsIgnoreCase(RiskResultFragment.class.getSimpleName())) {
            onActionRiskResultFragment(uri);
        }
    }

    private void onActionMainFragment(Uri uri) {
        String type = uri.getQueryParameter(Strings.INTENT_ACTION_TYPE);
        String urlIndex = uri.getQueryParameter(Strings.INTNET_CONTENT_URL);
        String title = uri.getQueryParameter(Strings.INTENT_TITLE);
        if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerPage.toString())) {
            changeFragment(CancerFragment.newInstance(urlIndex, title), true);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.riskInspection.toString())){
            changeFragment(RiskInspectionFragment.newInstance(HttpConfigure.getUrlRiskInspection(), title), true);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.search.toString())) {
            changeFragment(SearchFragment.newInstance(title), true);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerArticleQuestion.toString())) {
            String url = urlIndex.replaceAll("!!!", "#");
            changeFragment(CancerArticleQuestionFragment.newInstance(url, title), true);
        }
    }

    private void onActionRiskResultFragment(Uri uri) {
        String urlIndex = uri.getQueryParameter(Strings.INTNET_CONTENT_URL);
        String title = uri.getQueryParameter(Strings.INTENT_TITLE);
        String type = uri.getQueryParameter(Strings.INTENT_ACTION_TYPE);
        if (type.equalsIgnoreCase(Strings.IntentActionUrlType.cancerPage.toString())) {
            changeFragment(CancerFragment.newInstance(urlIndex, title), false);
        } else if (type.equalsIgnoreCase(Strings.IntentActionUrlType.appointment.toString())){

        }
    }

    //get search records
    public ArrayList<SearchRecord> getSearchRecord() {
        return SearchRecord.get();
    }
}
