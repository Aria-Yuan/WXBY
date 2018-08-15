package com.example.joan.myapplication;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * author : duyb
 * time : 2018/01/17
 * desc :主页
 */
public class MainHomeActivity extends AppCompatActivity {
    private BottomNavigationViewEx bnve;
    private VpAdapter adapter;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        initView();
        initData();
        initBNVE();
        initEvent();

    }

    /**
     * init BottomNavigationViewEx envent
     */
    private void initEvent() {
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                resetToDefaultIcon();
                switch (item.getItemId()) {
                    case R.id.menu_main:
                        item.setIcon(R.drawable.main_selected);
                        //setIconSize();
                        position = 0;

                        break;
                    case R.id.menu_me:
                        item.setIcon(R.drawable.me_selected);
                        position = 2;
                        break;
                    case R.id.menu_empty: {
                        position = 1;
                        //此处return false且在FloatingActionButton没有自定义点击事件时 会屏蔽点击事件
                        //return false;
                    }
                    default:
                        break;
                }
                if (previousPosition != position) {
                    viewPager.setCurrentItem(position, false);
                    previousPosition = position;
                }

                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (1 == position) {
                    setContentView(R.layout.main);

                } else {
                    floatingActionButton.setImageResource(R.drawable.begin);

                }
               /* // 1 is center 此段结合屏蔽FloatingActionButton点击事件的情况使用
                  //在viewPage滑动的时候 跳过最中间的page也
                if (position >= 1) {
                    position++;
                }*/
                bnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
/**
 * fab 点击事件结合OnNavigationItemSelectedListener中return false使用
 */
        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainHomeActivity.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void resetToDefaultIcon(){
        MenuItem a =  bnve.getMenu().getItem(0);
        a.setIcon(R.drawable.main_unselect);
        MenuItem b =  bnve.getMenu().getItem(2);
        b.setIcon(R.drawable.me_unselected);
    }

    private void initView() {
        floatingActionButton = findViewById(R.id.fab);
        viewPager = findViewById(R.id.main_body);
        bnve = findViewById(R.id.bnve);
    }

    /**
     * create fragments
     */
    private void initData() {
        fragments = new ArrayList<>(3);
        BaseFragment homeFragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position", "0");
        homeFragment.setArguments(bundle);

        BaseFragment orderFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("position", "1");
        orderFragment.setArguments(bundle);

        BaseFragment meFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("position", "2");
        meFragment.setArguments(bundle);


        fragments.add(homeFragment);
        fragments.add(orderFragment);
        fragments.add(meFragment);
    }

    /**
     * init BottomNavigationViewEx
     */
    private void initBNVE() {

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
}