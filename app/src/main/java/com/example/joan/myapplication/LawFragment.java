package com.example.joan.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joan.myapplication.DatePicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LawFragment extends Fragment {
    private String position;
    private int panjue = 0, caidin = 0, valid = 0, abandon = 0;

    View  view_search= null;
    View view_recommend = null;

    private TextView currentDateStart = null, currentDateEnd = null;
    private CustomDatePicker customDatePicker1, customDatePicker2;

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
//                    .findViewById(R.id.submit).setVisibility(View.VISIBLE);

                    initOnClickListener();

                }
                return view_search;
            }
            case "1":{
                //View a = inflater.inflate(R.layout.main_me, container, false);
                if(view_recommend == null){
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                    container.findViewById(R.id.submit).setVisibility(View.GONE);
                }

                return view_recommend;
            }
        }
        return view_recommend;
    }

    private void initOnClickListener(){
//        final RadioGroup item = view_search.findViewById(R.id.item);
//        view_search.findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                panjue = 0;
//                if(caidin == 0){
//                    caidin = 1;
//                }else{
//                    caidin = 0;
//                    item.clearCheck();
//                }
//            }
//        });
//        view_search.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                caidin = 0;
//                if(panjue == 0){
//                    panjue = 1;
//                }else{
//                    panjue = 0;
//                    item.clearCheck();
//                }
//            }
//        });

        final RadioGroup state = view_search.findViewById(R.id.state);
        view_search.findViewById(R.id.valid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panjue = 0;
                if(valid == 0){
                    valid = 1;
                }else{
                    valid = 0;
                    state.clearCheck();
                }
            }
        });
        view_search.findViewById(R.id.abandon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid = 0;
                if(abandon == 0){
                    abandon = 1;
                }else{
                    abandon = 0;
                    state.clearCheck();
                }
            }
        });


        currentDateStart = (TextView) view_search.findViewById(R.id.start);
        currentDateEnd = (TextView) view_search.findViewById(R.id.end);

        initDatePicker();

        currentDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd
                //拿到InputMethodManager
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                //如果window上view获取焦点 && view不为空
                if(imm.isActive() && getActivity().getCurrentFocus()!=null) {
                    //拿到view的token 不为空
                    getActivity().getCurrentFocus().clearFocus();
//                                view_search.findViewById(R.id.down_start).setFocusable(true);
//                                view_search.findViewById(R.id.down_start).requestFocus();
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                customDatePicker1.show(currentDateStart.getText().toString());
            }
        });

        currentDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd
                //拿到InputMethodManager
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                //如果window上view获取焦点 && view不为空
                if(imm.isActive() && getActivity().getCurrentFocus()!=null) {
                    //拿到view的token 不为空
                    getActivity().getCurrentFocus().clearFocus();
//                                view_search.findViewById(R.id.down_end).setFocusable(true);
//                                view_search.findViewById(R.id.down_end).requestFocus();
                    if (getActivity().getCurrentFocus().getWindowToken() != null) {
                        //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                showEndDate();
            }
        });

        view_search.findViewById(R.id.down_start).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentDateStart.setText(null);
                currentDateStart.setHint("請選擇");
                view_search.findViewById(R.id.down_start).setVisibility(View.GONE);
            }
        });

        view_search.findViewById(R.id.down_end).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentDateEnd.setText(null);
                currentDateEnd.setHint("請選擇");
                view_search.findViewById(R.id.down_end).setVisibility(View.GONE);
            }
        });
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
//        currentDateStart.setText(now.split(" ")[0]);
//        currentDateEnd.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateStart.setText(time.split(" ")[0]);
                view_search.findViewById(R.id.down_start).setVisibility(View.VISIBLE);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateEnd.setText(time.split(" ")[0]);
                view_search.findViewById(R.id.down_end).setVisibility(View.VISIBLE);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 不显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    private void showEndDate(){
        if(currentDateStart.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "請選擇開始時間", Toast.LENGTH_SHORT).show();
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            String now = sdf.format(new Date());
            customDatePicker2 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) { // 回调接口，获得选中的时间
                    currentDateEnd.setText(time.split(" ")[0]);
                    view_search.findViewById(R.id.down_end).setVisibility(View.VISIBLE);
                }
            }, currentDateStart.getText().toString() + " 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
            customDatePicker2.showSpecificTime(false); // 不显示时和分
            customDatePicker2.setIsLoop(false); // 允许循环滚动

            customDatePicker2.show(currentDateStart.getText().toString());
        }
    }

}
