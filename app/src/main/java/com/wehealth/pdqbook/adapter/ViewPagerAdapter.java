package com.wehealth.pdqbook.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2016/12/11.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> _views;
    private ArrayList<String> _titles;

    public ViewPagerAdapter(ArrayList<View> viewArrayList, ArrayList<String> titleArrayList) {
        this._views = viewArrayList;
        this._titles = titleArrayList;
    }

    @Override
    public int getCount() {
        return _views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(_views.get(position));
        return _views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(_views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _titles.get(position);
    }
}
