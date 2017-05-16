package com.wehealth.pdqbook.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        ViewPager viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
        final ImageView index1 = (ImageView) findViewById(R.id.welcome_pager_index1);
        final ImageView index2 = (ImageView) findViewById(R.id.welcome_pager_index2);
        final ImageView index3 = (ImageView) findViewById(R.id.welcome_pager_index3);
        final LinearLayout indexLay = (LinearLayout) findViewById(R.id.welcome_pager_index_lay);
        final TextView enterTx = (TextView) findViewById(R.id.welcome_enter);

        final ArrayList<View> views = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.mipmap.welcome1);
        views.add(img1);
        titles.add("");

        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.mipmap.welcome2);
        views.add(img2);
        titles.add("");

        ImageView img3 = new ImageView(this);
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
                Intent intent = new Intent(WelcomeActivity.this, PDQActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
    }

}
