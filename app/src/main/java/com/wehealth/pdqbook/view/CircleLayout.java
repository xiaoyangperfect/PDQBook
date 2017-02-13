package com.wehealth.pdqbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wehealth.pdqbook.R;

/**
 * @Author yangxiao on 2/14/2017.
 */

public class CircleLayout extends ViewGroup {

    private static final boolean CAN_SLIDE = false;

    private int mRadius;
    //default child item size
    private static final float ITEM_DEFAULT_DIMENSION = 1 / 4f;
    //center child item size
    private static final float CENTER_DIMENSION = 1 / 3f;
    //default padding size
    private static final float PADDING_LAYOUT = 1 / 12f;
    /*
    *the max value finger slip.
    * if circle view move value per second is greater than 300,
     * the view is beginning to auto slip
     */
    private static final int AUTO_FLING_VALUE = 300;
    //if the angle of slip is greater than this value, we will shield click event.
    private static final int FLING_STATUS_VALUE = 3;

    private int mFlingValue = AUTO_FLING_VALUE;

    private float mPadding;
    //start angle
    private double mStartAngle = 0;
    //item show texts
    private String[] mItemTexts;
    //item show images
    private int[] mItemImages;
    //item number
    private int mItemCount;
    //the angle of rotation
    private float mSlidingAngle;
    //time of rotation
    private long mSlidingTime;

    private boolean isFling;
    //the circular sector view
    private int mItemLayout = R.layout.item_circle_menu;

    public CircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {
            resWidth = getSuggestedMinimumWidth();
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else {
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);

        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

        int count = getChildCount();
        int childViewRadius = (int) (mRadius * ITEM_DEFAULT_DIMENSION);
        int childViewMode = MeasureSpec.EXACTLY;

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE)
                continue;

            int makeMeasureSpace = -1;
//            if (childView.getId() == R.id.id_circle_menu_item_center_layout) {
//                makeMeasureSpace = MeasureSpec.makeMeasureSpec((int) (mRadius * CENTER_DIMENSION),
//                        childViewMode);
//            } else {
//                makeMeasureSpace = MeasureSpec.makeMeasureSpec(childViewRadius,
//                        childViewMode);
//            }
            makeMeasureSpace = MeasureSpec.makeMeasureSpec(childViewRadius, childViewMode);
            childView.measure(makeMeasureSpace, makeMeasureSpace);
        }
        mPadding = PADDING_LAYOUT * mRadius;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutRadius = mRadius;
        int childCount = getChildCount();
        //view margin
        int left, top;
        int viewWidth = (int) (layoutRadius * ITEM_DEFAULT_DIMENSION);
        float viewAngle = 360f / (childCount - 1);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == GONE) {
                continue;
            }
            if (childView.getId() == R.id.id_circle_menu_item_center_layout) {
                left = layoutRadius / 2 - childView.getMeasuredWidth() / 2;
                int cr = left + childView.getMeasuredWidth();
                childView.layout(left, left, cr, cr);
                continue;
            }

            mStartAngle %= 360;
            float tmp = layoutRadius / 2f - viewWidth / 2 - mPadding;

            left = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                    * viewWidth);

            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
                    * viewWidth);

            childView.layout(left, top, left + viewWidth, top + viewWidth);
            // 叠加尺寸
            mStartAngle += viewAngle;
        }

//        View cView = findViewById(R.id.id_circle_menu_item_center_layout);
//        if (cView != null) {
//            int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
//            int cr = cl + cView.getMeasuredWidth();
//            cView.layout(cl, cl, cr, cr);
//        }
    }

    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;

    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!CAN_SLIDE) {
            return super.dispatchTouchEvent(event);
        }
        float x = event.getX();
        float y = event.getY();

        // Log.e("TAG", "x = " + x + " , y = " + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mSlidingTime = System.currentTimeMillis();
                mSlidingAngle = 0;

                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                /**
                 * 获得开始的角度
                 */
                float start = getAngle(mLastX, mLastY);
                /**
                 * 获得当前的角度
                 */
                float end = getAngle(x, y);

                // Log.e("TAG", "start = " + start + " , end =" + end);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mSlidingAngle += end - start;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += start - end;
                    mSlidingAngle += start - end;
                }
                // 重新布局
                requestLayout();

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:

                // 计算，每秒移动的角度
                float anglePerSecond = mSlidingAngle * 1000
                        / (System.currentTimeMillis() - mSlidingTime);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mSlidingAngle) > FLING_STATUS_VALUE) {
                    return true;
                }

                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 主要为了action_down时，返回true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }
    }

    public void setItemImages(int[] res) {
        mItemImages = res;
        if (mItemImages == null) {
            throw new IllegalArgumentException("angle item's iamge resource cannot be null!");
        }
        mItemCount = mItemImages.length;
        addAngleMenuItem();
    }

    private void addAngleMenuItem() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mItemCount; i++) {
            final int j = i;
            View view = mInflater.inflate(mItemLayout, this, false);
            if (i == mItemCount -1) {
                view.setId(R.id.id_circle_menu_item_center_layout);
            }
            ImageView iv = (ImageView) view.findViewById(R.id.id_circle_menu_item_image);
            if (iv != null) {
                iv.setImageResource(mItemImages[i]);
                if (mClickListener != null) {
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mClickListener.edgeItemClick(view, j);
                        }
                    });
                }
            }
            addView(view);
        }
    }

//    private void setCenterItemImage(int id) {
//        LayoutInflater mInflater = LayoutInflater.from(getContext());
//        View view = mInflater.inflate(mItemLayout, this, false);
//        view.setId(R.id.id_circle_menu_item_center_layout);
//        ImageView iv = (ImageView) view.findViewById(R.id.id_circle_menu_item_image);
//        if (iv != null) {
//            iv.setImageResource(id);
//            if (mClickListener != null) {
//                view.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mClickListener.centerItemClick(view);
//                    }
//                });
//            }
//        }
//        addView(view);
//    }

    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    public interface OnCircleLayoutItemClickListener {
        void edgeItemClick(View view, int position);

        void centerItemClick(View view);
    }

    private OnCircleLayoutItemClickListener mClickListener;

    public void setOnClickListener(OnCircleLayoutItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * 自动滚动的任务
     *
     * @author zhy
     */
    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30);
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            postDelayed(this, 30);
            // 重新布局
            requestLayout();
        }
    }
}
