package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.bson.types.ObjectId;

public class SearchCasesActivity  extends AppCompatActivity implements CaseOneLineView.OnRootClickListener{
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
    }

    private void searchCase(){
        SearchCasesActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EditText keyword = findViewById(R.id.keyword);
        EditText year = findViewById(R.id.year);
        EditText zihao = findViewById(R.id.zihao);
        EditText num = findViewById(R.id.number);
        EditText start = findViewById(R.id.start);
        EditText end = findViewById(R.id.end);
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
                Intent intent = new Intent(v.getContext(), SearchCasesActivity.class);
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
                intent.setClass(v.getContext(), SearchCaseDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }break;
        }

    }


}
