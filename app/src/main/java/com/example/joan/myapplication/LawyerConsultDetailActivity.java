package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class LawyerConsultDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String id;
    private Lawyer lawyer = new Lawyer();
    private TextView name, detail, year, times, scholar, occupation, special, personality;
    private Button question, back, follow, seeAll;
    private String[] text = {"向", "律師付費提問(NT$", ")"};
    private Case[] cases = new Case[3];
    private LayoutInflater li;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_consult_detail);

        initView();
        setData();

    }

    private void initView() {

        getID();
        getLawyer();

        name = findViewById(R.id.consult_lawyer_detail_name);
        detail = findViewById(R.id.consult_lawyer_detail_detail);
        year = findViewById(R.id.consult_lawyer_detail_year);
        times = findViewById(R.id.consult_lawyer_detail_times);
        scholar = findViewById(R.id.consult_lawyer_detail_scholar);
        occupation = findViewById(R.id.consult_lawyer_detail_occupation);
        special = findViewById(R.id.consult_lawyer_detail_special);
        personality = findViewById(R.id.consult_lawyer_detail_personal);
        question = findViewById(R.id.consult_lawyer_detail_question);
        back = findViewById(R.id.consult_lawyer_detail_back);
        follow = findViewById(R.id.consult_lawyer_detail_follow);
        seeAll = findViewById(R.id.consult_lawyer_detail_seeall);
        
        back.setOnClickListener(this);
        question.setOnClickListener(this);
        follow.setOnClickListener(this);
        seeAll.setOnClickListener(this);

        li = LayoutInflater.from(this);
        ll = findViewById(R.id.consult_lawyer_detail_cases);

    }

    @Override
    public void onClick(View view) {
        
        switch (view.getId()){
            case R.id.consult_lawyer_detail_back:
                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;
            case R.id.consult_lawyer_detail_question:
                questionTheLawyer();
                break;
            case R.id.consult_lawyer_detail_follow:
                followTheLawyer();
                break;
            case R.id.consult_lawyer_detail_seeall:
                seeAll();
        }
        
    }

    private void questionTheLawyer() {
        Intent intent = new Intent(LawyerConsultDetailActivity.this, QuestionLawyerActivity.class);
        intent.putExtra("lawyerID", id);
        intent.putExtra("name", lawyer.getLname());
        intent.putExtra("fee", lawyer.getFee());
        startActivity(intent);
        overridePendingTransition(R.anim.right, R.anim.left);
    }

    private void followTheLawyer(){
        overridePendingTransition(R.anim.right, R.anim.left);
    }

    private void seeAll(){
        Intent intent = new Intent(LawyerConsultDetailActivity.this, LawyerConsultAllCasesActivity.class);
        intent.putExtra("lawyerID", id);
        intent.putExtra("name", lawyer.getLname());
        startActivity(intent);
        overridePendingTransition(R.anim.right, R.anim.left);
    }

    public void getID() {

        Intent intent = getIntent();
        id = intent.getStringExtra("lawyerID");
        System.out.println(id);

    }

    private void getLawyer() {
        lawyer.setId(id);
        lawyer.setBranch("Branch");
        lawyer.setFee("Fee");
        lawyer.setIdentity("Identity");
        lawyer.setRate("4.9");
        lawyer.setResponse("Response");
        lawyer.setSpecial("Special");
        lawyer.setLname("Name" + id.toString());
        lawyer.setYear(10);
        lawyer.setTimes(9999);
        lawyer.setScholar("Scholar");
        lawyer.setPersonality("Personality");

        getCases();
    }

    private void getCases() {

        for (int i = 0; i < 3; i ++){
            Case newCase = new Case();
            newCase.setBrief("This is the case brief");
            newCase.setCaseDate("2018/9/8");
            newCase.setCaseTime("19:59");
            newCase.setLawyerID(lawyer.getId());
            newCase.setUserID("用戶123456");
            cases[i] = newCase;
        }

    }

    private void setData() {

        name.setText(lawyer.getLname().toString());
        detail.setText(lawyer.getBranch().toString() + " " + lawyer.getIdentity().toString());
        year.setText(String.valueOf(lawyer.getYear()));
        times.setText(String.valueOf(lawyer.getTimes()));
        scholar.setText(lawyer.getScholar().toString());
        occupation.setText(lawyer.getBranch().toString() + " " + lawyer.getIdentity().toString());
        special.setText(lawyer.getSpecial().toString());
        personality.setText(lawyer.getPersonality().toString());
        question.setText(text[0] + lawyer.getLname().toString() + text[1] + lawyer.getFee().toString() + text[2]);

        setCases();

    }

    private void setCases() {

        for (Case singleCase: cases) {
            View view = li.inflate(R.layout.sample_lawyer_consult_single_case, null);
            setCaseData(view, singleCase);
            setCaseListener(view, singleCase.getID());
            ll.addView(view);
        }

    }

    private void setCaseData(View view, Case singleCase) {

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

    private void setCaseListener(View view, final String id) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerConsultDetailActivity.this, LawyerConsultResultActivity.class);
                intent.putExtra("caseID", id);
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });
    }

}
