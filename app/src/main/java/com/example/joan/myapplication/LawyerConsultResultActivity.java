package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LawyerConsultResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back, favor, follow, question;
    private TextView title, name, identity, score;
    private String[] scoreList = {"極差", "較差", "一般", "推薦" , "力薦"};
    private String id, lawyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_consult_result);

        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        id = intent.getStringExtra("caseID");

        getCaseData();
        getLawyerData();

        back = findViewById(R.id.consult_lawyer_result_back);
        favor = findViewById(R.id.consult_lawyer_result_favor);
        follow = findViewById(R.id.consult_lawyer_result_follow);
        question = findViewById(R.id.consult_lawyer_result_question);

        back.setOnClickListener(this);
        favor.setOnClickListener(this);
        follow.setOnClickListener(this);
        question.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.consult_lawyer_result_back:
                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;
            case R.id.consult_lawyer_result_favor:
                favorTheCase();
                break;
            case R.id.consult_lawyer_result_follow:
                followTheLawyer();
                break;
            case R.id.consult_lawyer_result_question:
                questionTheLawyer();
                break;
        }

    }

    private void questionTheLawyer() {
    }

    private void followTheLawyer() {
    }

    private void favorTheCase() {
    }

    public void getCaseData() {

    }

    private void getLawyerData() {


    }
}
