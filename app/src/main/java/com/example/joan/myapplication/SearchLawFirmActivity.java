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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.repository.LawFirmRepositoryImpl;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class SearchLawFirmActivity extends AppCompatActivity implements FirmOneLineView.OnRootClickListener{
    TabLayout tabLayout;
    ViewPager viewpager;
    private List<String> tabs;
    private List<Fragment> fragments;

    private EditText input;

    private LawFirmRepositoryImpl lawFirmRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_law_firm);

        initTabLayout();
        initDatas();
        initViewPager();
        initView();

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
        viewpager.setAdapter(new VpAdapter(getSupportFragmentManager(), fragments));
    }

    private void initDatas() {
        tabs = new ArrayList<>();
        tabs.add("推薦律所");
        tabs.add("按地區");
        tabs.add("按分類");

        input = findViewById(R.id.et_search);

        fragments = new ArrayList<>(3);
        FirmFragment homeFragment = new FirmFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position", "0");
        homeFragment.setArguments(bundle);

        FirmFragment orderFragment = new FirmFragment();
        bundle = new Bundle();
        bundle.putString("position", "1");
        orderFragment.setArguments(bundle);

        FirmFragment meFragment = new FirmFragment();
        bundle = new Bundle();
        bundle.putString("position", "2");
        meFragment.setArguments(bundle);


        fragments.add(homeFragment);
        fragments.add(orderFragment);
        fragments.add(meFragment);
    }

    private void initView(){

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //输入后调用该方法
            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().length() == 0) {
                    //若搜索框为空,则隱藏清空按鈕
                    findViewById(R.id.ivDeleteText).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.ivDeleteText).setVisibility(View.VISIBLE);
                }
            }
        });

        // 搜索框的键盘搜索键
        // 点击回调
        findViewById(R.id.iv_search).setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键
            // 修改回车键功能
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 隐藏键盘，这里getCurrentFocus()需要传入Activity对象，如果实际不需要的话就不用隐藏键盘了，免得传入Activity对象，这里就先不实现了
//                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
//                    boolean hasData = hasData(et_search.getText().toString().trim());
//                    if (!hasData) {
//                        insertData(et_search.getText().toString().trim());
//
//                        queryData("");
//                    }
//                    //根据输入的内容模糊查询商品，并跳转到另一个界面，这个需要根据需求实现
//                    Toast.makeText(context, "点击搜索", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        //点击搜索按钮后的事件
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean hasData = hasData(et_search.getText().toString().trim());
//                if (!hasData) {
//                    insertData(et_search.getText().toString().trim());
//
//                    //搜索后显示数据库里所有搜索历史是为了测试
//                    queryData("");
//                }
//                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
                SearchLawFirmActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                searchLawFirm("Asia");
                input.setHint("找什麼呢");
                input.setText("");
            }
        });

        //清空输入框
        findViewById(R.id.ivDeleteText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setHint("找什麼呢");
                input.setText("");
            }
        });

        //返回按鈕
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

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

    private void searchLawFirm(String condition){
//        List<LawFirmModel> firms = lawFirmRepository.findByCondition("Asia");

//        new Thread(){
//            @Override
//            public void run(){
//                //把要联网的代码放在这里
//                lawFirmRepository = new LawFirmRepositoryImpl();
//
//                MongoClientURI mongoDBurl = new MongoClientURI("" +
//                        "mongodb://dajiayiqibiye:wxby@wxby-shard-00-00-7ea9c.mongodb.net:27017,wxby-shard-00-01-7ea9c.mongodb.net:27017,wxby-shard-00-02-7ea9c.mongodb.net:27017/test?ssl=true&replicaSet=WXBY-shard-0&authSource=admin&retryWrites=true");
//                MongoClient mongoClient = new MongoClient(mongoDBurl);
//                System.out.println(mongoClient.toString()+"&&&&&&&&&&&&&&&");
//                MongoDatabase db = mongoClient.getDatabase("wxby");
//                MongoCollection<Document> collection = db.getCollection("law_firm");
//                FindIterable<Document> a = collection.find().limit(1);
//                //System.out.println("***********" + a.first().get("firm_addr"));
//
        setContentView(R.layout.law_firm_search_result);
        LinearLayout result = findViewById(R.id.law_firm_result);
        for(int i = 0 ; i < 10; i++){
            result.addView(new FirmOneLineView(getBaseContext())
                    .init("我们真的能毕业吗事务所","新北市新专区中正路","hahaha")
                    .setOnRootClickListener(this, new ObjectId()));
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SearchLawFirmActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        System.out.println("&&&&&&&&" + firms.get(0).getAddress());

//        for (LawFirmModel firm: firms) {
//            result.addView(new FirmOneLineView(getBaseContext())
//                    .init(firm.getAddress(),firm.getAddress(),firm.getType())
//                    .setOnRootClickListener(this, firm.getId()));
//        }


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
                intent.setClass(v.getContext(), SearchLawFirmDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }break;
        }

    }


}
