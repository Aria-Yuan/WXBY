package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CaseConsultResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private Button back, favor;
    private ProgressBar bar;
    private ViewPager pager;
    private List<Fragment> list;
    public CaseResultData result = new CaseResultData();
    private CaseResultSimilarListFragment similarList;
    private CaseResultReasonListFragment reasonList;
    private CaseResultReferListFragment referList;
    private CaseResultCompensateListFragment compensateList;
    private TextView percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_consult_result);

        getData();
        initItems();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            setData();
        }else{
        }

    }

    protected void initItems(){

        back = findViewById(R.id.consult_case_result_back);
        favor = findViewById(R.id.consult_case_result_favor);
        bar = findViewById(R.id.consult_case_result_percent);
        tabLayout = findViewById(R.id.consult_case_result_tablayout);
        pager = findViewById(R.id.consult_case_result_pager);
        bar = findViewById(R.id.consult_case_result_percent);
        percent = findViewById(R.id.consult_case_result_number);

        back.setOnClickListener(this);
        favor.setOnClickListener(this);

        similarList = new CaseResultSimilarListFragment();
        reasonList = new CaseResultReasonListFragment();
        referList = new CaseResultReferListFragment();
        compensateList = new CaseResultCompensateListFragment();

        initBar();
        initTabLayout();

    }

    private void initBar() {

        bar.setIndeterminate(false);
        bar.setProgress(result.percentage);
        percent.setText(String.valueOf(result.percentage/100));

    }

    protected void initTabLayout(){

        list = new ArrayList<>();
        list.add(similarList);
        list.add(reasonList);
        list.add(referList);
        list.add(compensateList);

        pager.setAdapter(new CaseConsultAdapter(getSupportFragmentManager(),list));
        tabLayout.setupWithViewPager(pager);

        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_similar));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_reason));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_refer));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_compensate));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setSimilarData();
                        break;
                    case 1:
                        setReasonData();
                        break;
                    case 2:
                        setReferData();
                        break;
                    case 3:
                        setCompensateData();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setSimilarData();
                        break;
                    case 1:
                        setReasonData();
                        break;
                    case 2:
                        setReferData();
                        break;
                    case 3:
                        setCompensateData();
                        break;
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setSimilarData();
                        break;
                    case 1:
                        setReasonData();
                        break;
                    case 2:
                        setReferData();
                        break;
                    case 3:
                        setCompensateData();
                }
            }
        });

    }

    protected void getData(){

        CaseResultData crd = new CaseResultData();
        result.percentage = 8000;
        result.similars[0] = crd.new Similar("testTitle1", "testSubTitle1", "testLawyer1");
        result.similars[1] = crd.new Similar("testTitle2", "testSubTitle2", "testLawyer2");
        result.similars[2] = crd.new Similar("testTitle3", "testSubTitle3", "testLawyer3");
        result.reasons[0] = crd.new Reason("testName1");
        result.reasons[1] = crd.new Reason("testName2");
        result.reasons[2] = crd.new Reason("testName3");
        result.refers[0] = crd.new Refer("testRefer1", "testSubTitle1");
        result.refers[1] = crd.new Refer("testRefer2", "testSubTitle2");
        result.refers[2] = crd.new Refer("testRefer3", "testSubTitle3");
        result.compensates = crd.new Compensate(new int[]{10, 10, 10}, new int[]{10, 10, 10}, new int[]{10, 10, 10});

    }

    protected void setSimilarData(){
        similarList = (CaseResultSimilarListFragment)pager.getAdapter().instantiateItem(pager, 0);
        similarList.initData(result.similars);
    }

    protected void setReasonData(){
        reasonList = (CaseResultReasonListFragment)pager.getAdapter().instantiateItem(pager, 1);
        reasonList.initData(result.reasons);
    }

    protected void setReferData(){
        referList = (CaseResultReferListFragment)pager.getAdapter().instantiateItem(pager, 2);
        referList.initData(result.refers);
    }

    protected void setCompensateData(){
        compensateList = (CaseResultCompensateListFragment)pager.getAdapter().instantiateItem(pager, 3);
        compensateList.initData(result.compensates);
    }

    protected void setData(){

        setSimilarData();
//        setReasonData();
//        setReferData();
//        setCompensateData();

//        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
//        if (similarList == null) System.out.println("You fault again!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        else System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
//        similarList.initData(result.similars);
//        LinearLayout ll = similarList.getView().findViewById(R.id.case_consult_result_linear);
//
//        LayoutInflater li = LayoutInflater.from(similarList.getContext());
//        View view = li.inflate(R.layout.case_result_single_similar, null);

//        ll.addView(findViewById(R.id.case_result_similar_single_title));
//        ll.addView(findViewById(R.id.case_result_similar_single_title));
//        ll.addView(findViewById(R.id.case_result_similar_single_title));
//
//        tabLayout.setupWithViewPager(pager);

//        if (similarList.getView() == null) System.out.println("WTF!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        else System.out.println("000000000000000000000000000000000000");
//         LinearLayout ll = similarList.getView().findViewById(R.id.case_consult_result_linear);
//         ll.addView(new case_result_similar_single(this));
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.case_consult_result_linear, new CaseResultSimilarFragment());
//        ft.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.consult_case_result_back:
                Intent intent = new Intent(this, ConsultingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;

        }

    }
}
