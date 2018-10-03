package com.example.joan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LawyerConsultSortedListActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll;
    private LayoutInflater li;
    private int lNumber = 10;
    private TextView title, name, identity, branch, special,response, fee, rate, image;
    private List<Lawyer> totalData = new ArrayList<>();
    private Intent intent;
    private String sortName;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_consult_sorted_list);

        initView();
        addView();

    }

    private void initView() {
        ll = findViewById(R.id.consult_lawyer_sorted_list);
        li = LayoutInflater.from(this);

        intent = getIntent();
        sortName = intent.getStringExtra("sortName");

        title = findViewById(R.id.consult_lawyer_sorted_list_title);
        title.setText(sortName.toString());

        back = findViewById(R.id.consult_lawyer_sorted_list_back);
        back.setOnClickListener(this);

    }

    public void addView() {
        List<Lawyer> list = getData();
        for (Lawyer lawyer: list){
            View view = li.inflate(R.layout.sample_lawyer_consult_single_lawyer, null);
            setData(view, lawyer);
            setListener(view, lawyer.getId());
            ll.addView(view);
        }
    }

    private void setListener(View view, final String id) {//跳转至律师详情// 页面
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerConsultSortedListActivity.this, LawyerConsultDetailActivity.class);
                intent.putExtra("lawyerID", id.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.right, R.anim.left);
            }
        });
    }

    private List getData(){
        List<Lawyer> list = new ArrayList();
        for (int i = 1; i <= lNumber; i ++){
            list.add(new Lawyer("000" + i, "Lawyer" + i, "Identity" + i, "Branch" + i, "Special" + i,
                    "Response" + i, "Fee" + i, "Rate" + i, "Image"));
        }
        totalData.addAll(list);
        return list;
    }

    private void setData(View view, Lawyer lawyer){

        name = view.findViewById(R.id.lawyer_consult_single_name);
        identity = view.findViewById((R.id.lawyer_consult_single_identity));
        branch = view.findViewById((R.id.lawyer_consult_single_branch));
        special = view.findViewById((R.id.lawyer_consult_single_special));
        response = view.findViewById((R.id.lawyer_consult_single_responce));
        fee = view.findViewById((R.id.lawyer_consult_single_fee));
        rate = view.findViewById((R.id.lawyer_consult_single_rate));
//        image = view.findViewById((R.id.lawyer_consult_single_image));

        name.setText(lawyer.getLname());
        identity.setText(lawyer.getIdentity());
        branch.setText(lawyer.getBranch());
        special.setText(lawyer.getSpecial());
        response.setText(lawyer.getResponse());
        fee.setText(lawyer.getFee());
        rate.setText(lawyer.getRate());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.consult_lawyer_sorted_list_back:
                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;
        }
    }
}
