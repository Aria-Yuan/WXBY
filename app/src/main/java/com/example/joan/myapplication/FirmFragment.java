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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.repository.LawFirmRepositoryImpl;

import net.sf.json.JSONArray;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class FirmFragment  extends Fragment implements FirmOneLineView.OnRootClickListener{
    private String position;
    List<LawFirmModel> firmList;

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

    public void FirmView(List<LawFirmModel> firmList){
        getActivity().setContentView(R.layout.law_firm_search_result);
        LinearLayout result = getActivity().findViewById(R.id.law_firm_result);
        int index = 0;
        for (LawFirmModel firm: firmList
                ) {
            result.addView(new FirmOneLineView(getContext())
                    .init(firm.getName(), firm.getAddress() ,"hahaha")
                    .setOnRootClickListener(this, index));
        }

        getActivity().findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SearchLawFirmActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onRootClick(View v) {
//        LawFirmModel firm = lawFirmRepository.findById((ObjectId)v.getTag());
//        setContentView(R.layout.law_firm_detail);
//
        switch (v.getId()){
            case R.id.btn_back:{
                getActivity().findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        getActivity().finish();
                    }
                });
            }break;

            default:{
                Intent intent=new Intent();
                intent.setClass(v.getContext(), SearchLawFirmDetailActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bunble = new Bundle();
                LawFirmModel l = firmList.get((int)v.getTag());
                bunble.putSerializable("firm", l);
                intent.putExtras(bunble);
                startActivity(intent);
            }break;
        }

    }

}
