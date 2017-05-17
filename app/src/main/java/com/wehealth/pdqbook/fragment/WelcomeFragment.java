package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.adapter.ViewPagerAdapter;
import com.wehealth.pdqbook.tool.Strings;

import java.util.ArrayList;

/**
 */
public class WelcomeFragment extends BaseFragment {


    private OnFragmentInteractionListener mListener;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        initView(view);
        setWindowNoLimit();
        return view;
    }

    private void initView(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.welcome_viewpager);
        final ImageView index1 = (ImageView) view.findViewById(R.id.welcome_pager_index1);
        final ImageView index2 = (ImageView) view.findViewById(R.id.welcome_pager_index2);
        final ImageView index3 = (ImageView) view.findViewById(R.id.welcome_pager_index3);
        final LinearLayout indexLay = (LinearLayout) view.findViewById(R.id.welcome_pager_index_lay);
        final TextView enterTx = (TextView) view.findViewById(R.id.welcome_enter);

        final ArrayList<View> views = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ImageView img1 = new ImageView(getContext());
        img1.setBackgroundResource(R.mipmap.welcome1);
        views.add(img1);
        titles.add("");

        ImageView img2 = new ImageView(getContext());
        img2.setBackgroundResource(R.mipmap.welcome2);
        views.add(img2);
        titles.add("");

        ImageView img3 = new ImageView(getContext());
        img3.setBackgroundResource(R.mipmap.welcome3);
        views.add(img3);
        titles.add("");

        ViewPagerAdapter adapter = new ViewPagerAdapter(views, titles);
        viewPager.setAdapter(adapter);

        final Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(700);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    index1.setBackgroundResource(R.drawable.bg_circle_selected_index);
                    index2.setBackgroundResource(R.drawable.bg_circle_select_index);
                    index3.setBackgroundResource(R.drawable.bg_circle_select_index);
                } else if (position == 1){
                    index1.setBackgroundResource(R.drawable.bg_circle_select_index);
                    index2.setBackgroundResource(R.drawable.bg_circle_selected_index);
                    index3.setBackgroundResource(R.drawable.bg_circle_select_index);
                    indexLay.setVisibility(View.VISIBLE);
                    enterTx.setVisibility(View.GONE);
                } else {
                    index1.setBackgroundResource(R.drawable.bg_circle_select_index);
                    index2.setBackgroundResource(R.drawable.bg_circle_select_index);
                    index3.setBackgroundResource(R.drawable.bg_circle_selected_index);
                    indexLay.setVisibility(View.GONE);
                    enterTx.startAnimation(animation);
                    enterTx.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        enterTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = Strings.getIntentUri(WelcomeFragment.class.getSimpleName());
                onButtonPressed(Uri.parse(uri));
            }
        });
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
    public void onDestroyView() {
        setWindowLimit();
        super.onDestroyView();
    }

    private void setWindowNoLimit() {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setWindowLimit() {
//        WindowManager.LayoutParams attr = getActivity().getWindow()
//                .getAttributes();
//        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().getWindow().setAttributes(attr);
        getActivity().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
