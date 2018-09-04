package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class LawFragment extends Fragment {
    private String position;

    View  view_search= null;
    View view_recommend = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        position = getArguments().getString("position");
        //熱門問答區fragment初始化

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TextView textView = view.findViewById(R.id.tv_title);
        //textView.setText(title);
        switch (position){
            case "0":{
                if(view_search == null){

                    view_search = inflater.inflate(R.layout.search_law_search, container, false);
                }
                return view_search;
            }
            case "1":{
                //View a = inflater.inflate(R.layout.main_me, container, false);
                if(view_recommend == null){
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                }

                return view_recommend;
            }
        }
        return view_recommend;
    }

}
