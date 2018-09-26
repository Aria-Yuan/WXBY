package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joan.myapplication.DatePicker.CustomDatePicker;
import com.example.joan.myapplication.database.model.BaseModel;
import com.example.joan.myapplication.database.model.JudgementModel;
import com.example.joan.myapplication.database.repository.CounselingRepositoryImpl;
import com.example.joan.myapplication.database.repository.JudgementRepositoryImpl;

import net.sf.json.JSONArray;

import org.bson.types.ObjectId;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchCasesActivity  extends AppCompatActivity implements CaseOneLineView.OnRootClickListener{
    private TextView currentDateStart = null, currentDateEnd = null;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private List<JudgementModel> judgementList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_cases);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchCase();
            }
        });

        currentDateStart = (TextView) findViewById(R.id.start);
        currentDateEnd = (TextView) findViewById(R.id.end);

        initDatePicker();

        findViewById(R.id.down_start).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 日期格式为yyyy-MM-dd

                //拿到InputMethodManager
                InputMethodManager imm = (InputMethodManager)SearchCasesActivity.this.getSystemService(SearchCasesActivity.this.INPUT_METHOD_SERVICE);
                //如果window上view获取焦点 && view不为空
                if(imm.isActive() && SearchCasesActivity.this.getCurrentFocus()!=null) {
                    //拿到view的token 不为空
                    SearchCasesActivity.this.getCurrentFocus().clearFocus();
//                                view_search.findViewById(R.id.down_start).setFocusable(true);
//                                view_search.findViewById(R.id.down_start).requestFocus();
                    if (SearchCasesActivity.this.getCurrentFocus().getWindowToken() != null) {
                        //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                        imm.hideSoftInputFromWindow(SearchCasesActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                customDatePicker1.show(currentDateStart.getText().toString());
            }
        });

        findViewById(R.id.down_end).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 日期格式为yyyy-MM-dd

                //拿到InputMethodManager
                InputMethodManager imm = (InputMethodManager)SearchCasesActivity.this.getSystemService(SearchCasesActivity.this.INPUT_METHOD_SERVICE);
                //如果window上view获取焦点 && view不为空
                if(imm.isActive() && SearchCasesActivity.this.getCurrentFocus()!=null) {
                    //拿到view的token 不为空
                    SearchCasesActivity.this.getCurrentFocus().clearFocus();
//                                view_search.findViewById(R.id.down_end).setFocusable(true);
//                                view_search.findViewById(R.id.down_end).requestFocus();
                    if (SearchCasesActivity.this.getCurrentFocus().getWindowToken() != null) {
                        //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                        imm.hideSoftInputFromWindow(SearchCasesActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                showEndDate();
            }
        });
    }

    private void searchCase(){
        SearchCasesActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EditText keyword = findViewById(R.id.keyword);
        EditText year = findViewById(R.id.year);
        EditText zihao = findViewById(R.id.zihao);
        EditText num = findViewById(R.id.number);
        TextView start = findViewById(R.id.start);
        TextView end = findViewById(R.id.end);
        EditText reason = findViewById(R.id.reason);
        EditText content = findViewById(R.id.content);
        EditText judge = findViewById(R.id.judge);

        try{
            RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/searchJudgement.action");
            params.addQueryStringParameter("condition","");
            params.addQueryStringParameter("type","0");
            setContentView(R.layout.search_case_result);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JSONArray jArray= JSONArray.fromObject(s);
                    System.out.println(jArray);
                    judgementList = new JudgementRepositoryImpl().convert(jArray);

                    initView();
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
                intent.setClass(v.getContext(), SearchCaseDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", judgementList.get((int)v.getTag()).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }break;
        }

    }

    private void initView(){
        LinearLayout result = findViewById(R.id.case_result);
        for(int i = 0 ; i < judgementList.size(); i++){
            String[] a = judgementList.get(i).getjId().split(" ",2);
            System.out.println(judgementList.get(i).getjId());
            result.addView(new CaseOneLineView(getBaseContext())
                    .init(a[0],a[1].split(" \\[")[0],judgementList.get(i).getjReason(),"#民事")
                    .setOnRootClickListener(this, i));
        }

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), SearchCasesActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
//        currentDateStart.setText(now.split(" ")[0]);
//        currentDateEnd.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(SearchCasesActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateStart.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(SearchCasesActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateEnd.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 不显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    private void showEndDate(){
        if(currentDateStart.getText().toString().isEmpty()){
            Toast.makeText(SearchCasesActivity.this, "請選擇開始時間", Toast.LENGTH_SHORT).show();
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            String now = sdf.format(new Date());
            customDatePicker2 = new CustomDatePicker(SearchCasesActivity.this, new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) { // 回调接口，获得选中的时间
                    currentDateEnd.setText(time.split(" ")[0]);
                }
            }, currentDateStart.getText().toString() + " 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
            customDatePicker2.showSpecificTime(false); // 不显示时和分
            customDatePicker2.setIsLoop(false); // 允许循环滚动

            customDatePicker2.show(currentDateStart.getText().toString());
        }
    }
}
