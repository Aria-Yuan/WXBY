package com.example.joan.myapplication;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joan.myapplication.DatePicker.CustomDatePicker;
import com.example.joan.myapplication.database.model.BaseModel;
import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.model.LawModel;
import com.example.joan.myapplication.database.repository.LawFirmRepositoryImpl;
import com.example.joan.myapplication.database.repository.LawRepositoryImpl;

import net.sf.json.JSONArray;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import me.shihao.library.XRadioGroup;

public class SearchLawActivity extends AppCompatActivity implements LawOneLineView.OnRootClickListener {
    TabLayout tabLayout;
    ViewPager viewpager;
    private List<String> tabs;
    private List<Fragment> fragments;

    List<LawModel> lawList;
    String condition;

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

    @Override
    protected void onStart(){
        super.onStart();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition()==1){
                    findViewById(R.id.submit).setVisibility(View.INVISIBLE);
                    InputMethodManager imm = (InputMethodManager) SearchLawActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null){
                        imm.toggleSoftInput(0,0);
                    }
                }else{
                    findViewById(R.id.submit).setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) SearchLawActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null){
                        imm.toggleSoftInput(1,0);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        EditText and = findViewById(R.id.and);
        EditText or = findViewById(R.id.or);
        EditText not = findViewById(R.id.not);
        TextView start = findViewById(R.id.start);
        TextView end = findViewById(R.id.end);
        EditText num = findViewById(R.id.number);

        try{
            RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/searchLaw.action");
            Document d_condition = new Document();
            if(!keyword.getText().toString().isEmpty()){
                d_condition.append("keyword",keyword.getText().toString());
            }
            if(!and.getText().toString().isEmpty()){
                d_condition.append("and",and.getText().toString());
            }
            if(!or.getText().toString().isEmpty()){
                d_condition.append("or",or.getText().toString());
            }
            if(!not.getText().toString().isEmpty()){
                d_condition.append("not",not.getText().toString());
            }

            condition = d_condition.toJson();
            params.addQueryStringParameter("condition",condition);
            params.addQueryStringParameter("type", "0");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JSONArray jArray= JSONArray.fromObject(s);
                    lawList = new LawRepositoryImpl().convert(jArray);

                    LawView(lawList);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void LawView(List<LawModel> lawList){
        setContentView(R.layout.search_law_result);
        LinearLayout result = findViewById(R.id.law_result);
        for(int i = 0 ; i < lawList.size(); i++){
            result.addView(new LawOneLineView(getBaseContext())
                    .init(lawList.get(i).getName(),lawList.get(i).getContent(),"#民事")
                    .setOnRootClickListener(this, i));
        }
        if(lawList.size()==0){
            result.addView(new FindNothingView(getBaseContext()).init());
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), SearchLawActivity.class);
//                startActivity(intent);
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
                LawModel l = lawList.get((int)v.getTag());
                Intent intent=new Intent();
                intent.setClass(v.getContext(), SearchLawDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bunble = new Bundle();
                bunble.putSerializable("law", l);
                intent.putExtras(bunble);
                startActivity(intent);
            }break;
        }

    }

}
