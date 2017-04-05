    package com.wehealth.pdqbook.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.wehealth.pdqbook.R;

    /**
     * colorful arc progress bar
     * Created by shinelw on 12/4/15.
     */
    public class ColorArcProgressBar extends View{

        //直径
        private int diameter = 500;

        //圆心
        private float centerX;
        private float centerY;

        private Paint vTextPaint;
        private Paint advicePaint;
        private Paint curSpeedPaint;
        private Paint largeBorderCirclePaint;
        private Paint progressCirclePaint;
        private Paint middleCirclePaint;
        private Paint smallCirclePaint;

        private RectF bgRect;
        private RectF centerRect;

        private ValueAnimator progressAnimator;

        private float startAngle = 135;
        private float sweepAngle = 270;
        private float currentAngle = 0;
        private float lastAngle;
        private int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.parseColor("#FF7F00"), Color.RED};
        private float maxValues = 100;
        private float curValues = 0;
        private String curText;
        private float bgArcWidth = dipToPx(2);
        private float progressWidth = dipToPx(10);
        private float textSize = dipToPx(18);
        private float hintSize = dipToPx(15);
        private float curSpeedSize = dipToPx(13);
        private int aniSpeed = 1000;
        private float longDegree = dipToPx(13);
        private float shortDegree = dipToPx(5);
        private final int DEGREE_PROGRESS_DISTANCE = dipToPx(8);
        private int hintColor = Color.WHITE;
        private String hintString = "";
        private boolean isNeedTitle;
        private boolean isNeedUnit;
        private boolean isNeedContent;
        private String titleString;



        // sweepAngle / maxValues 的值
        private float k;

        public ColorArcProgressBar(Context context) {
            super(context, null);
            initView(context);
        }

        public ColorArcProgressBar(Context context, AttributeSet attrs) {
            super(context, attrs, 0);
            initConfig(context, attrs);
            initView(context);
        }

        public ColorArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initConfig(context, attrs);
            initView(context);
        }

        /**
         * 初始化布局配置
         * @param context
         * @param attrs
         */
        private void initConfig(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar);
            int color1 = a.getColor(R.styleable.ColorArcProgressBar_front_color1, colors[0]);
            int color2 = a.getColor(R.styleable.ColorArcProgressBar_front_color2, colors[1]);
            int color3 = a.getColor(R.styleable.ColorArcProgressBar_front_color3, colors[2]);
            int color4 = a.getColor(R.styleable.ColorArcProgressBar_front_color4, colors[3]);
            int color5 = a.getColor(R.styleable.ColorArcProgressBar_front_color5, colors[4]);
            colors = new int[]{color1, color2, color3, color4, color5};

            sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_total_engle, 270);
            bgArcWidth = a.getDimension(R.styleable.ColorArcProgressBar_back_width, dipToPx(2));
            progressWidth = a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10));
            isNeedTitle = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_title, false);
            isNeedContent = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_content, false);
            isNeedUnit = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_unit, false);
            hintString = a.getString(R.styleable.ColorArcProgressBar_string_unit);
            titleString = a.getString(R.styleable.ColorArcProgressBar_string_title);
            curValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 0);
            maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 100);
            setCurrentValues(curValues);
            setMaxValues(maxValues);
            a.recycle();

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int width = (int) (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE);
            int height= (int) (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE);
            setMeasuredDimension(width, height);
        }

        private void initView(Context context) {

            diameter = 7 * getScreenWidth() / 10;
            //弧形的矩阵区域
            bgRect = new RectF();
            bgRect.top = longDegree + progressWidth/2 + DEGREE_PROGRESS_DISTANCE;
            bgRect.left = longDegree + progressWidth/2 + DEGREE_PROGRESS_DISTANCE;
            bgRect.right = diameter + (longDegree + progressWidth/2 + DEGREE_PROGRESS_DISTANCE);
            bgRect.bottom = diameter + (longDegree + progressWidth/2 + DEGREE_PROGRESS_DISTANCE);
            centerRect = new RectF();
            centerRect.top = longDegree + progressWidth + DEGREE_PROGRESS_DISTANCE;
            centerRect.left = longDegree + progressWidth + DEGREE_PROGRESS_DISTANCE;
            centerRect.right = diameter + (longDegree + DEGREE_PROGRESS_DISTANCE);
            centerRect.bottom = diameter + (longDegree + DEGREE_PROGRESS_DISTANCE);

            //圆心
            centerX = (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE)/2;
            centerY = (2 * longDegree + progressWidth + diameter + 2 * DEGREE_PROGRESS_DISTANCE)/2;

            //整个弧形
            largeBorderCirclePaint = new Paint();
            largeBorderCirclePaint.setAntiAlias(true);
            largeBorderCirclePaint.setStyle(Paint.Style.STROKE);
            largeBorderCirclePaint.setStrokeWidth(bgArcWidth);
            largeBorderCirclePaint.setColor(context.getResources().getColor(R.color.base_line));
            largeBorderCirclePaint.setStrokeCap(Paint.Cap.ROUND);

            RadialGradient gradient = new RadialGradient(centerX, centerY, getMiddleRadius() + 10,
                    new int[]{Color.WHITE, Color.parseColor("#FAFAFA")},
                    new float[]{1f, 0.95f}, Shader.TileMode.MIRROR);
            middleCirclePaint = new Paint();
            middleCirclePaint.setShader(gradient);

            //当前进度的弧形
            progressCirclePaint = new Paint();
            progressCirclePaint.setAntiAlias(true);
            progressCirclePaint.setStyle(Paint.Style.STROKE);
            progressCirclePaint.setStrokeCap(Paint.Cap.ROUND);
            progressCirclePaint.setStrokeWidth(progressWidth);
            progressCirclePaint.setColor(Color.GREEN);

            //内容显示文字
            vTextPaint = new Paint();
            vTextPaint.setTextSize(textSize);
            vTextPaint.setColor(Color.WHITE);
            vTextPaint.setTextAlign(Paint.Align.CENTER);

            //显示单位文字
            advicePaint = new Paint();
            advicePaint.setTextSize(hintSize);
            advicePaint.setColor(hintColor);
            advicePaint.setTextAlign(Paint.Align.CENTER);

            //显示标题文字
            curSpeedPaint = new Paint();
            curSpeedPaint.setTextSize(curSpeedSize);
            curSpeedPaint.setColor(hintColor);
            curSpeedPaint.setTextAlign(Paint.Align.CENTER);

            smallCirclePaint = new Paint();
            smallCirclePaint.setStrokeWidth(15);

        }



        @Override
        protected void onDraw(Canvas canvas) {

            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

            //整个弧
            canvas.drawArc(bgRect, startAngle, sweepAngle, false, largeBorderCirclePaint);

            canvas.drawCircle(centerX, centerY, getMiddleRadius(), middleCirclePaint);

            smallCirclePaint.setColor(getCurrentColor(curValues));
            canvas.drawCircle(centerX, centerY, getSmallRadius(), smallCirclePaint);

            //设置渐变色
            SweepGradient sweepGradient = new SweepGradient(centerX, centerY, colors, null);
            Matrix matrix = new Matrix();
            matrix.setRotate(130, centerX, centerY);
            sweepGradient.setLocalMatrix(matrix);
            progressCirclePaint.setShader(sweepGradient);


            //当前进度
            canvas.drawArc(bgRect, startAngle, currentAngle, false, progressCirclePaint);

            if (isNeedContent) {
                //String.format("%.0f", curValues)
                canvas.drawText(curText, centerX, centerY, vTextPaint);
            }
            if (isNeedUnit) {
                canvas.drawText(hintString, centerX, centerY + textSize, advicePaint);
            }
            if (isNeedTitle) {
                canvas.drawText(titleString, centerX, centerY - 2 * textSize / 3, curSpeedPaint);
            }

            invalidate();
        }

        /**
         * 设置最大值
         * @param maxValues
         */
        public void setMaxValues(float maxValues) {
            this.maxValues = maxValues;
            k = sweepAngle/maxValues;
        }

        /**
         * 设置当前值
         * @param currentValues
         */
        public void setCurrentValues(float currentValues) {
            if (currentValues > maxValues) {
                currentValues = maxValues;
            }
            if (currentValues < 0) {
                currentValues = 0;
            }
            this.curValues = currentValues;
            lastAngle = currentAngle;
            setAnimation(lastAngle, currentValues * k, aniSpeed);
            this.curText = "0";
            if (currentValues <= 20) {
                curText = getResources().getString(R.string.risk_0_2);
                hintString = getResources().getString(R.string.advise_0_2);
            } else if (20 < currentValues && currentValues <= 40) {
                curText = getResources().getString(R.string.risk_2_4);
                hintString = getResources().getString(R.string.advise_2_4);
            } else if (40 < currentValues && currentValues <= 60) {
                curText = getResources().getString(R.string.risk_4_6);
                hintString = getResources().getString(R.string.advise_4_6);
            } else if (60 < currentValues && currentValues <= 80) {
                curText = getResources().getString(R.string.risk_6_8);
                hintString = getResources().getString(R.string.advise_6_8);
            } else if (80 < currentValues && currentValues <= 100) {
                curText = getResources().getString(R.string.risk_8_10);
                hintString = getResources().getString(R.string.advise_8_10);
            }
        }

        /**
         * 设置整个圆弧宽度
         * @param bgArcWidth
         */
        public void setBgArcWidth(int bgArcWidth) {
            this.bgArcWidth = bgArcWidth;
        }

        /**
         * 设置进度宽度
         * @param progressWidth
         */
        public void setProgressWidth(int progressWidth) {
            this.progressWidth = progressWidth;
        }

        /**
         * 设置速度文字大小
         * @param textSize
         */
        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        /**
         * 设置单位文字大小
         * @param hintSize
         */
        public void setHintSize(int hintSize) {
            this.hintSize = hintSize;
        }

        /**
         * 设置单位文字
         * @param hintString
         */
        public void setUnit(String hintString) {
            this.hintString = hintString;
            invalidate();
        }

        /**
         * 设置直径大小
         * @param diameter
         */
        public void setDiameter(int diameter) {
            this.diameter = dipToPx(diameter);
        }

        /**
         * 为进度设置动画
         * @param last
         * @param current
         */
        private void setAnimation(float last, float current, int length) {
            progressAnimator = ValueAnimator.ofFloat(last, current);
            progressAnimator.setDuration(length);
            progressAnimator.setTarget(currentAngle);
            progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentAngle= (float) animation.getAnimatedValue();
                    curValues = currentAngle/k;
                }
            });
            progressAnimator.start();
        }


        /**
         * dip 转换成px
         * @param dip
         * @return
         */
        private int dipToPx(float dip) {
            float density = getContext().getResources().getDisplayMetrics().density;
            return (int)(dip * density + 0.5f * (dip >= 0 ? 1 : -1));
        }

        /**
         * 得到屏幕宽度
         * @return
         */
        private int getScreenWidth() {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }

        /**
         * 获取最小圆半径
         * @return
         */
        private float getSmallRadius() {
            return diameter / 4;
        }

        /**
         * 获取中间圆半径
         * @return
         */
        private float getMiddleRadius() {
            return diameter / 2 - bgArcWidth / 2;
        }

        /**
         * 颜色渐变算法
         * 获取某个百分比下的渐变颜色值
         *
         * @param value
         * @return
         */
        private int getCurrentColor(float value) {
            float percent = value / 100;
            float[][] f = new float[colors.length][3];
            for (int i = 0; i < colors.length; i++) {
                f[i][0] = (colors[i] & 0xff0000) >> 16;
                f[i][1] = (colors[i] & 0x00ff00) >> 8;
                f[i][2] = (colors[i] & 0x0000ff);
            }
            float[] result = new float[3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < f.length; j++) {
                    if (f.length == 1 || percent == j / (f.length - 1f)) {
                        result = f[j];
                    } else {
                        if (percent > j / (f.length - 1f) && percent < (j + 1f) / (f.length - 1)) {
                            result[i] = f[j][i] - (f[j][i] - f[j + 1][i]) * (percent - j / (f.length - 1f)) * (f.length - 1f);
                        }
                    }
                }
            }
            return Color.rgb((int) result[0], (int) result[1], (int) result[2]);
        }


    }
