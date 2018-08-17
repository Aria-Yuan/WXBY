package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirmFragment  extends Fragment {
    private String position;

    View view_recommend = null;
    View view_district = null;
    View view_type = null;

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
                if(view_recommend == null){
                }
                return view_recommend;
            }
            case "1":{
                //View a = inflater.inflate(R.layout.main_me, container, false);
                if(view_district == null){
                    view_district = inflater.inflate(R.layout.search_law_firm_district, container, false);

                }

                return view_district;
            }
        }
        return view_recommend;
    }

}
