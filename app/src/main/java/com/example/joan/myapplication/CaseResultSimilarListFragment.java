package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.database.model.JudgementModel;

import java.util.List;

public class CaseResultSimilarListFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll;
    private List<JudgementModel> similars;

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
            view.setId(i);
            view.setOnClickListener(this);
            ll.addView(view);
        }
//        System.out.println("InitSimilar!!!!!!!!!!!!!!!");
    }

    public void initData(List<JudgementModel> similar){

        similars = similar;

//        View single = new case_result_similar_single(getContext());
        for(int i = 0; i < 3; i ++){
            View view = ll.getChildAt(i);
//            Button lawyer;
            TextView title, subTitle, number, time;
//            View view = new CaseResultSimilarFragment().getView();
//            lawyer = view.findViewById(R.id.case_result_similar_single_lawyer);
            title = view.findViewById(R.id.case_result_similar_single_title);
            subTitle = view.findViewById(R.id.case_result_similar_single_subtitle);
            number = view.findViewById(R.id.case_result_similar_single_number);
            time = view.findViewById(R.id.case_result_similar_single_time);
//            result = view.findViewById(R.id.case_result_similar_single_number);
//            lawyer.setText(similar.get(i).getjId());
            title.setText(similar.get(i).getjId());
            subTitle.setText(similar.get(i).getjReason());
            number.setText((i+1)+".");
            time.setText(similar.get(i).getjDate());
//            result.setText(resultt);
//            ft.add(R.id.case_consult_result_linear, new CaseResultSimilarFragment());

//            ll.addView(single);
//            CaseResultSimilarFragment single = new CaseResultSimilarFragment();
//            single.init(similars[i], i + 1);
//            ll.addView(single.getView().findViewById(R.id.case_result_similar_single_title));

        }
//        ft.commit();
    }

    @Override
    public void onClick(View view) {

        System.out.println(similars.get(view.getId()).getId());
        String id = similars.get(view.getId()).getId();
        Intent intent = new Intent(getContext(), JudgementConsultActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }
}
