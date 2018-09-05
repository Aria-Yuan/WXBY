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

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchCasesActivity  extends AppCompatActivity implements CaseOneLineView.OnRootClickListener{
    private TextView currentDateStart = null, currentDateEnd = null;
    private CustomDatePicker customDatePicker1, customDatePicker2;

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

        setContentView(R.layout.search_case_result);
        LinearLayout result = findViewById(R.id.case_result);
        for(int i = 0 ; i < 10; i++){
            result.addView(new CaseOneLineView(getBaseContext())
                    .init("我们真的能毕业吗法院","103年度板簡字53號","返還房屋租賃","民事")
                    .setOnRootClickListener(this, new ObjectId()));
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
                startActivity(intent);
            }break;
        }

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
