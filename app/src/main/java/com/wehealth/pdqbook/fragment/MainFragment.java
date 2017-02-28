package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.repertory.CancerDataConfigure;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.view.CircleLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        RelativeLayout searchLayout = (RelativeLayout) view.findViewById(R.id.mainpage_search_layout);
        searchLayout.setOnClickListener(this);
        RelativeLayout aboutLayout = (RelativeLayout) view.findViewById(R.id.mainpage_about_layout);
        aboutLayout.setOnClickListener(this);

        final CancerDataConfigure[] cancers = new CancerDataConfigure[] {
                CancerDataConfigure.ShiDao,
                CancerDataConfigure.Gan,
                CancerDataConfigure.ZhiChang,
                CancerDataConfigure.Wei,
                CancerDataConfigure.Fei,
                CancerDataConfigure.RuXian,
                CancerDataConfigure.Nao
        };

        int[] images = new int[cancers.length];
        for (int i = 0; i < cancers.length; i++) {
            images[i] = cancers[i].getImageResource();
        }

        CircleLayout circleLayout = (CircleLayout) view.findViewById(R.id.mainpage_circlelayout);
        circleLayout.setOnClickListener(new CircleLayout.OnCircleLayoutItemClickListener() {
            @Override
            public void edgeItemClick(View view, int position) {
                onCancerClick(cancers[position]);
            }

            @Override
            public void centerItemClick(View view) {
            }
        });
//        circleLayout.setCenterItemImage();
        circleLayout.setItemImages(images);
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
    public void onClick(View v) {
        String url = "";
        String urlType = "";
        String title = "";
        switch (v.getId()) {
            case R.id.mainpage_search_layout:
                urlType = Strings.IntentActionUrlType.search.toString();
                title = getString(R.string.search);
                break;
            case R.id.mainpage_about_layout:
                urlType = Strings.IntentActionUrlType.riskInspection.toString();
                title = getString(R.string.risk_inspection);
                break;
        }
        String uri = Strings.getIntentUri(MainFragment.class.getSimpleName(),
                Strings.INTNET_CONTENT_URL, url,
                Strings.INTENT_ACTION_TYPE, urlType,
                Strings.INTENT_TITLE, title);

        onButtonPressed(Uri.parse(uri));
    }

    //call when user click cancer button
    private void onCancerClick(CancerDataConfigure data) {
        String url = String.valueOf(data.getIndex());
        String urlType = Strings.IntentActionUrlType.cancerPage.toString();
        String title = getResources().getString(data.getTextResource());
        String uri = Strings.getIntentUri(MainFragment.class.getSimpleName(),
                Strings.INTNET_CONTENT_URL, url,
                Strings.INTENT_ACTION_TYPE, urlType,
                Strings.INTENT_TITLE, title);
        onButtonPressed(Uri.parse(uri));
    }
}
