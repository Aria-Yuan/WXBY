package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CaseResultReferListFragment extends Fragment {

    private LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_case_result_list_refer, null);
        ll = view.findViewById(R.id.case_consult_refer_linear);
        initView();
        return view;
    }

    public void initView(){
        LayoutInflater li = LayoutInflater.from(getContext());
        for (int i = 0; i < 3; i ++){
            View view = li.inflate(R.layout.sample_case_result_single_refer, null);
            ll.addView(view);
        }
        if (ll == null){
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("NULL  in initView!!!!!!!!!!!!!!!");
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("-------------------");
        }else{
                System.out.println("-------------------");
                System.out.println("-------------------");
                System.out.println("-------------------");
                System.out.println("NOT   NULL!!!!!!!!!!!!!!!");
                System.out.println("-------------------");
                System.out.println("-------------------");
                System.out.println("-------------------");

        }
    }

    public void initData(CaseResultData.Refer[] refers){

        if (ll == null){
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("NULL!!!!!!!!!!!!!!!");
            System.out.println("-------------------");
            System.out.println("-------------------");
            System.out.println("-------------------");
        }

//        View single = new case_result_similar_single(getContext());
        for(int i = 0; i < 3; i ++){
            View view = ll.getChildAt(i);
            TextView title, number, subtitle;
//            View view = new CaseResultSimilarFragment().getView();
            title = view.findViewById(R.id.case_result_refer_single_title);
            number = view.findViewById(R.id.case_result_refer_single_number);
            subtitle = view.findViewById(R.id.case_result_refer_single_subtitle);
            title.setText(refers[i].title);
            subtitle.setText(refers[i].subtitle);
            number.setText((i+1)+".");
//            ft.add(R.id.case_consult_result_linear, new CaseResultSimilarFragment());

//            ll.addView(single);
//            CaseResultSimilarFragment single = new CaseResultSimilarFragment();
//            single.init(similars[i], i + 1);
//            ll.addView(single.getView().findViewById(R.id.case_result_similar_single_title));

        }
//        ft.commit();
    }
}
