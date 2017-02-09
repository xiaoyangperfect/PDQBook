package com.wehealth.pdqbook.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * @Author yangxiao on 2/13/2017.
 */

public class FragPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private ArrayList<Fragment> mFragment;
    private ArrayList<String> mTitles;

    public FragPagerAdapter(FragmentManager fm, @NonNull ArrayList<Fragment> fragments, @NonNull ArrayList<String> titles) {
        super(fm);
        this.mFragment = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
