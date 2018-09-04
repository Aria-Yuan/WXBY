package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class SearchLawActivity extends AppCompatActivity implements CaseOneLineView.OnRootClickListener{
    TabLayout tabLayout;
    ViewPager viewpager;
    private List<String> tabs;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_law);

        initTabLayout();
        initDatas();
        initViewPager();

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchLaw();
            }
        });
    }

    private void initTabLayout() {
        tabLayout = findViewById(R.id.main_tab);
        viewpager = findViewById(R.id.main_body);
//        //指示条的颜色
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
//        tabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.indicatorHeight));
        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        tabLayout.setupWithViewPager(viewpager);
        //自定义标签布局
//        for (int i = 0; i < tabs.size(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.frag_base, tabLayout, false);
//            tv.setText(tabs.get(i));
//            tab.setCustomView(tv);
//        }
    }

    private void initViewPager() {
        viewpager.setAdapter(new SearchLawActivity.VpAdapter(getSupportFragmentManager(), fragments));
    }

    private void initDatas() {
        tabs = new ArrayList<>();
        tabs.add("法規檢索");
        tabs.add("熱門法規");

        fragments = new ArrayList<>(3);
        LawFragment homeFragment = new LawFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position", "0");
        homeFragment.setArguments(bundle);

        LawFragment orderFragment = new LawFragment();
        bundle = new Bundle();
        bundle.putString("position", "1");
        orderFragment.setArguments(bundle);

        fragments.add(homeFragment);
        fragments.add(orderFragment);
    }

    private class VpAdapter extends FragmentPagerAdapter {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

    private void searchLaw(){
        SearchLawActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EditText keyword = findViewById(R.id.keyword);
        EditText year = findViewById(R.id.year);
        EditText zihao = findViewById(R.id.zihao);
        EditText num = findViewById(R.id.number);
        EditText start = findViewById(R.id.start);
        EditText end = findViewById(R.id.end);
        EditText reason = findViewById(R.id.reason);
        EditText content = findViewById(R.id.content);
        EditText judge = findViewById(R.id.judge);

        setContentView(R.layout.search_law_result);
        LinearLayout result = findViewById(R.id.law_result);
        for(int i = 0 ; i < 10; i++){
            result.addView(new CaseOneLineView(getBaseContext())
                    .init("我们真的能毕业吗法院","103年度板簡字53號","返還房屋租賃","民事")
                    .setOnRootClickListener(this, new ObjectId()));
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SearchLawActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onRootClick(View v) {
//        LawFirmModel firm = lawFirmRepository.findById((ObjectId)v.getTag());
//        setContentView(R.layout.law_firm_detail);
//
        switch (v.getId()){
            case R.id.btn_back:{
                findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        finish();
                    }
                });
            }break;

            default:{
                Intent intent=new Intent();
                intent.setClass(v.getContext(), SearchLawDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }break;
        }

    }
}
