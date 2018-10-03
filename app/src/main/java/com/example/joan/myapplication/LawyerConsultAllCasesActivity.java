package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LawyerConsultAllCasesActivity extends AppCompatActivity implements View.OnClickListener {

    private String id, name, titleString = "律師的所有公開案件";
    private Button back;
    private TextView title;
    private LinearLayout ll;
    private LayoutInflater li;
    private List<Case> cases = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_consult_all_cases);

        getData();
        initView();

    }

    private void initView() {
        li = LayoutInflater.from(this);
        Intent intent = getIntent();

        id = intent.getStringExtra("lawyerID");
        name = intent.getStringExtra("name");

        back = findViewById(R.id.consult_lawyer_all_cases_back);
        title = findViewById(R.id.consult_lawyer_all_cases_title);
        ll = findViewById(R.id.consult_lawyer_all_cases_list);

        title.setText(name + titleString);
        back.setOnClickListener(this);

        initList();

    }

    private void initList() {

        for (Case singleCase: cases){
            View view = li.inflate(R.layout.sample_lawyer_consult_single_case, null);
            setData(view, singleCase);
            setListener(view, singleCase.getID());
            ll.addView(view);
        }

    }

    private void setData(View view, Case singleCase){

        TextView userName, brief, date, time, visit;

        userName = view.findViewById(R.id.lawyer_consult_single_case_username);
        brief = view.findViewById(R.id.lawyer_consult_single_case_brief);
        date = view.findViewById(R.id.lawyer_consult_single_case_date);
        time = view.findViewById(R.id.lawyer_consult_single_case_time);
        visit = view.findViewById(R.id.lawyer_consult_single_case_visit);

        userName.setText(singleCase.getUserID());
        brief.setText(singleCase.getBrief());
        date.setText(singleCase.getCaseDate());
        time.setText(singleCase.getCaseTime());
        if (!(singleCase.getVisit() == 0))
            visit.setText(singleCase.getVisit());

    }

    private void setListener(View view, String id) {
    }

    public void getData() {

        for (int i = 0; i < 10; i ++){
            Case newCase = new Case();
            newCase.setBrief("This is the case brief" + i);
            newCase.setCaseDate("2018/9/8");
            newCase.setCaseTime("19:59");
            newCase.setLawyerID(id);
            newCase.setUserID("用戶12345" + i);
            cases.add(newCase);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.consult_lawyer_all_cases_back:
                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;
        }
    }
}
