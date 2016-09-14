package com.startemplan.vyuspot;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;

public class Help extends Activity {

    MaterialRippleLayout mtp;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.help);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


            mtp = (MaterialRippleLayout) findViewById(R.id.ripple);

            mtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });

            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            ImagePagerAdapter adapter = new ImagePagerAdapter();
            viewPager.setAdapter(adapter);
        }

        private class ImagePagerAdapter extends PagerAdapter {
            private int[] mImages = new int[] {
                    R.drawable.screen1,
                    R.drawable.screen2,
                    R.drawable.screen3,
                    R.drawable.screen4,
                    R.drawable.screen5,
            };

            @Override
            public int getCount() {
                return mImages.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == ((ImageView) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Context context = Help.this;
                ImageView imageView = new ImageView(context);
                int padding = context.getResources().getDimensionPixelSize(
                        R.dimen.padding_medium);
                imageView.setPadding(padding, padding, padding, padding);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(mImages[position]);
                ((ViewPager) container).addView(imageView, 0);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((ImageView) object);
            }
        }
    }