package com.example.joan.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.myapplication.database.model.CounselingModel;
import com.example.joan.myapplication.database.model.LegalCounselingModel;

import java.util.List;

public class MyAnswerLawyerConsultActivity extends AppCompatActivity {

    private ImageView lawyerPicture;
    private LinearLayout content;
    private Button consult;
    private String[] text = {"回答", "提出的問題"};

    private LegalCounselingModel counseling;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_counseling_detail);

        counseling = (LegalCounselingModel) getIntent().getSerializableExtra("counseling");
        initView();
        initData();

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    private void initView(){
        lawyerPicture = findViewById(R.id.lawyer_picture);
        content = findViewById(R.id.counseling_main);
        consult = findViewById(R.id.lawyer_consult);
    }

    @SuppressLint("SetTextI18n")
    private void initData(){
//        lawyerPicture.sP
//        nameJob.setText(counseling.getLawyer().getName() + "  " + counseling.getLawyer().getJob());
//        firm.setText(counseling.getLawyer().getFirm());

        consult.setText(text[0] + counseling.getQuestioner() + text[1]);

        List<CounselingModel> counselings = counseling.getContent();

        for(int i = 0; i < counselings.size(); i++){
            CounselingModel cur = counselings.get(i);
            content.addView(new CounselingContentView(getBaseContext()).init(i+1,cur,counseling.getLawyer().getName()));
        }

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAnswerLawyerConsultActivity.this, AnswerConsultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("counseling", counseling);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });
    }
}
