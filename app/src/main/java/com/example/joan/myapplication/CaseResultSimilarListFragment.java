package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class CaseResultSimilarListFragment extends Fragment {

    private LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_case_result_list_similar, null);
        ll = view.findViewById(R.id.case_consult_similar_linear);
        initView();
        return view;
    }

    public void initView(){
        LayoutInflater li = LayoutInflater.from(getContext());
        for (int i = 0; i < 3; i ++){
            View view = li.inflate(R.layout.sample_case_result_single_similar, null);
            ll.addView(view);
        }
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("InitSimilar!!!!!!!!!!!!!!!");
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
    }

    public void initData(CaseResultData.Similar[] similars){

//        View single = new case_result_similar_single(getContext());
        for(int i = 0; i < 3; i ++){
            View view = ll.getChildAt(i);
            Button lawyer;
            TextView title, subTitle, number;
//            View view = new CaseResultSimilarFragment().getView();
            lawyer = view.findViewById(R.id.case_result_similar_single_lawyer);
            title = view.findViewById(R.id.case_result_similar_single_title);
            subTitle = view.findViewById(R.id.case_result_similar_single_subtitle);
            number = view.findViewById(R.id.case_result_similar_single_number);
            lawyer.setText(similars[i].lawyer);
            title.setText(similars[i].title);
            subTitle.setText(similars[i].subTitle);
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
