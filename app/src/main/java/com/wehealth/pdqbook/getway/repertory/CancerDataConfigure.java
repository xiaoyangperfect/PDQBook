package com.wehealth.pdqbook.getway.repertory;

import com.wehealth.pdqbook.R;

/**
 * @Author yangxiao on 2/14/2017.
 */

public enum CancerDataConfigure {
    ShiDao(R.drawable.btn_shidaoai_bg, R.string.shidaoai, 1),
    Gan(R.drawable.btn_ganai_bg, R.string.ganai, 2),
    ZhiChang(R.drawable.btn_zhichangai_bg, R.string.zhichangai, 3),
    Wei(R.drawable.btn_weiai_bg, R.string.weiai, 4),
    Fei(R.drawable.btn_feiai_bg, R.string.feiai, 5),
    RuXian(R.drawable.btn_ruxianai_bg, R.string.ruxianai, 6),
    Nao(R.drawable.btn_naoai_bg, R.string.naoai, 7);

    private int mImageResource;
    private int mTextResource;
    private int mIndex;

    CancerDataConfigure(int imageResource, int textResource, int index) {
        this.mImageResource = imageResource;
        this.mTextResource = textResource;
        this.mIndex = index;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public int getTextResource() {
        return mTextResource;
    }

    public int getIndex() {
        return mIndex;
    }
}
