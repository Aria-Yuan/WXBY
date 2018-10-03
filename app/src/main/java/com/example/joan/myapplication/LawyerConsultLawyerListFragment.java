package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LawyerConsultLawyerListFragment extends Fragment {

    private LinearLayout ll;
    private LayoutInflater li;
    private int lNumber = 10;
    private TextView name, identity, branch, special,response, fee, rate, image;
    private List<Lawyer> totalData = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_lawyer_consult_lawyer_list, null);

        initView(view);
        addView();

        return view;
    }

    private void initView(View view) {
        ll = view.findViewById(R.id.consult_lawyer_list);
        li = LayoutInflater.from(getContext());
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

    private void setListener(View view, final String id) {//跳转至律师详情页面
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LawyerConsultActivity.page, LawyerConsultDetailActivity.class);
                intent.putExtra("lawyerID", id.toString());
                startActivity(intent);
                LawyerConsultActivity.page.overridePendingTransition(R.anim.right, R.anim.left);
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

    public List getTotalList(){return totalData;}

    public void setlNumber(int num) {this.lNumber = num;}

    public int getlNumber(){return lNumber;}

}
