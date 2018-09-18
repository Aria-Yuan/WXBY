package com.example.joan.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment implements MyOneLineView.OnRootClickListener{
    private String position;
    TabLayout tabLayout;
    ViewPager viewpager;

    //当标签数目小于等于4个时，标签栏不可滑动
    public static final int MOVABLE_COUNT = 4;
    int tabCount = 6;
    private List<String> tabs;
    private List<Fragment> fragments;

    private static View view_main = null;
    private static View view_me = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        position = getArguments().getString("position");
        //熱門問答區fragment初始化

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TextView textView = view.findViewById(R.id.tv_title);
        //textView.setText(title);
        switch (position){
            case "0":{
                if(view_main == null){
                    view_main = inflater.inflate(R.layout.main, container, false);
                    setIconSize();
                }

                //下方热门问答显示窗口
                initTabLayout();
                initDatas();
                initViewPager();
                initMainButton();

                return view_main;
            }
            case "2":{
                //View a = inflater.inflate(R.layout.main_me, container, false);
                if(view_me == null){
                    view_me = inflater.inflate(R.layout.main_me, container, false);
                    //我的界面列表
                    LinearLayout list1 = view_me.findViewById(R.id.me_list_2);
                    list1.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.response, "我的咨詢", "", true)
                            .setOnRootClickListener(this, 1));
                    list1.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.counseling, "我收到的咨詢", "", true)
                            .setOnRootClickListener(this, 2));

                    LinearLayout list2 = view_me.findViewById(R.id.me_list_3);
                    list2.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.shoucang, "我的收藏", "", true)
                            .setOnRootClickListener(this, 3));
                    list2.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.follow, "我的關注", "", true)
                            .setOnRootClickListener(this, 4));

                    LinearLayout list3 = view_me.findViewById(R.id.me_list_1);
                    list3.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.wallet, "我的錢包", "", true)
                            .setOnRootClickListener(this, 5));
                    list3.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.cards, "我的卡券", "", true)
                            .setOnRootClickListener(this, 6));

                    LinearLayout list4 = view_me.findViewById(R.id.me_list_4);
                    list4.addView(new MyOneLineView(getContext())
                            .initMine(R.drawable.zhaofatiao, "關於我們", "", true)
                            .setOnRootClickListener(this, 7));
                }
                return view_me;
            }
        }
        return view_main;
    }

    private void setIconSize(){
        Button mNextButton=(Button)view_main.findViewById(R.id.search_lawyer);
        Drawable drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_lawyer);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.search_judgement);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_judgement);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.search_law_firm);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_firm);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.search_law);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_law);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.quick_counseling);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_counseling);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.unscramble);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_unscramble);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.schedule);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_schedule);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        mNextButton=(Button)view_main.findViewById(R.id.help);
        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_help);
        drawable.setBounds(0,0,100,100);
        mNextButton.setCompoundDrawables(null,drawable,null,null);

        TextView searchBox = (TextView) view_main.findViewById(R.id.edt_search);
        Drawable d = ContextCompat.getDrawable(getContext(),R.drawable.search);
        d.setBounds(0, 5, 65, 65);
        searchBox.setCompoundDrawables(d,null,null,null);
    }

    @Override
    public void onRootClick(View v) {
        switch ((int) v.getTag()) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    private void initTabLayout() {
        tabLayout = view_main.findViewById(R.id.main_tab);
        viewpager = view_main.findViewById(R.id.main_view);
        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        tabLayout.setTabMode(tabCount <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
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
        viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
    }

    private void initDatas() {
        tabs = new ArrayList<>();
        for (int i = 0; i < tabCount; i++) {
            tabs.add("标签" + i);
        }

        fragments = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(MainViewFragment.newInstance(tabs.get(i)));
        }

    }

    private void initMainButton(){
        final TextView search = (TextView) view_main.findViewById(R.id.edt_search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button search_case = view_main.findViewById(R.id.search_judgement);
        search_case.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchCasesActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button search_law = view_main.findViewById(R.id.search_law);
        search_law.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchLawActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button law_firm = view_main.findViewById(R.id.search_law_firm);
        law_firm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchLawFirmActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button quick_counseling = view_main.findViewById(R.id.quick_counseling);
        quick_counseling.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), CounselingActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button unscramble = view_main.findViewById(R.id.unscramble);
        unscramble.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), unFinishedActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button schedule = view_main.findViewById(R.id.schedule);
        schedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), unFinishedActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });

        Button help = view_main.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), unFinishedActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
       }
    }
}
