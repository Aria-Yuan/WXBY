package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.joan.myapplication.database.model.BaseModel;
import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.model.LegalCounselingModel;
import com.example.joan.myapplication.database.repository.CounselingRepositoryImpl;
import com.example.joan.myapplication.database.repository.LawFirmRepositoryImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.types.ObjectId;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.security.AccessController.getContext;

public class CounselingActivity extends AppCompatActivity implements CounselingResultLayout.OnRootClickListener{

    private EditText input;
    private String condition;

    private  List<LegalCounselingModel> counselingList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counselling_main);

        initDatas();
        initView();

    }

    private void initDatas() {
        input = findViewById(R.id.et_search);
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
                CounselingActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                condition = input.getText().toString();
                searchCounseling(condition);
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

    private void searchCounseling(String c){
        try{
            RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/searchCounseling.action");
            params.addQueryStringParameter("condition",c);
            params.addQueryStringParameter("type","0");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JSONArray jArray= JSONArray.fromObject(s);
                    counselingList = new CounselingRepositoryImpl().convert(jArray);
                    System.out.println(counselingList.get(0).getContent().get(0).getQuestion());

                    CounselingView(counselingList);
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

    public void CounselingView(List<LegalCounselingModel> counselingList){
        setContentView(R.layout.counseling_result);
        LinearLayout result = findViewById(R.id.counseling_result);
        if(counselingList.isEmpty()){
            result.addView(new FindNothingView(getBaseContext()).init());
        }else{
            int index = 0;
            for (LegalCounselingModel counseling: counselingList
                    ) {
                String question = counseling.getContent().get(0).getQuestion();

                String createTime = counseling.getCreateTime().replace('T',' ');
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString = formatter.format(createTime);

                String viewCount = counseling.getViewCount() + "个人看过";

                result.addView(new CounselingResultLayout(getBaseContext())
                        .init(question, createTime,viewCount)
                        .setOnRootClickListener(this, index));
                index++;
            }
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), CounselingActivity.class);
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
                LegalCounselingModel a = counselingList.get((int)v.getTag());
                int vc = a.getViewCount();
                vc++;
                a.setViewCount(vc);

                try{
                    RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/updateCounseling.action");
                    String firm;
                    firm = new CounselingRepositoryImpl().disconvert(a);
                    System.out.println(firm);
                    params.addQueryStringParameter("condition",firm);
                    params.addQueryStringParameter("type", "0");
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            JSONArray jArray= JSONArray.fromObject(s);
                            updateList();
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
                a = counselingList.get((int)v.getTag());
                Intent intent=new Intent();
                intent.setClass(v.getContext(), CounselingDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("counseling", a);
                intent.putExtras(bundle);
                startActivity(intent);
            }break;
        }
    }

    private void updateList(){
        try{
            RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/searchCounseling.action");
            params.addQueryStringParameter("condition",condition);
            params.addQueryStringParameter("type","0");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JSONArray jArray= JSONArray.fromObject(s);
                    counselingList = new CounselingRepositoryImpl().convert(jArray);
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
}
