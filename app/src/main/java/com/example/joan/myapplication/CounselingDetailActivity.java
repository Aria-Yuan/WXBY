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

import com.example.joan.myapplication.oneLineView.CounselingContentView;
import com.example.joan.myapplication.database.model.CounselingModel;
import com.example.joan.myapplication.database.model.LegalCounselingModel;

import java.util.List;

public class CounselingDetailActivity extends AppCompatActivity {

    private ImageView lawyerPicture;
    private TextView nameJob;
    private TextView firm;
    private LinearLayout content;
    private Button consult;
    private String[] text = {"向", "律師付費提問(NT$", ")"};

    private  LegalCounselingModel counseling;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counseling_detail);

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
        nameJob = findViewById(R.id.name_job);
        firm = findViewById(R.id.firm);
        content = findViewById(R.id.counseling_main);
        consult = findViewById(R.id.lawyer_consult);
    }

    @SuppressLint("SetTextI18n")
    private void initData(){
//        lawyerPicture.sP
//        nameJob.setText(counseling.getLawyer().getName() + "  " + counseling.getLawyer().getJob());
//        firm.setText(counseling.getLawyer().getFirm());

        nameJob.setText(counseling.getLawyer().getName() + " " + counseling.getLawyer().getJob());
        firm.setText(counseling.getLawyer().getCompany());
        consult.setText(text[0] + counseling.getLawyer().getName() + text[1] + counseling.getLawyer().getPrice() + text[2]);

        List<CounselingModel> counselings = counseling.getContent();

        for(int i = 0; i < counselings.size(); i++){
            CounselingModel cur = counselings.get(i);
            content.addView(new CounselingContentView(getBaseContext()).init(i+1,cur,counseling.getLawyer().getName()));
        }

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CounselingDetailActivity.this, QuestionLawyerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lawyer", counseling.getLawyer());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });


    }


}
