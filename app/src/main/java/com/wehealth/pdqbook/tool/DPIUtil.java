package com.wehealth.pdqbook.tool;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * 调用屏幕宽高之前必须先调用getScreenMetrics
 */
public class DPIUtil {

	private static float mDensity = DisplayMetrics.DENSITY_DEFAULT;
	public static float screen_width;
	public static float screen_height;

	public static void setDensity(float density) {
		mDensity = density;
	}
	public static float getDensity() {
		return mDensity;
	}

	public static int dip2px(Context context, float dipValue) {
		mDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (dipValue * mDensity + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		return (int) (pxValue / mDensity + 0.5f);
	}
	
	public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }

	public static float getWidthByHight() {
		return screen_width / screen_height;
	}

}
