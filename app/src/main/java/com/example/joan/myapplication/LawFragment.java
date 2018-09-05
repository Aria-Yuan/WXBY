package com.example.joan.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joan.myapplication.DatePicker.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LawFragment extends Fragment {
    private String position;

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

                    currentDateStart = (TextView) view_search.findViewById(R.id.start);
                    currentDateEnd = (TextView) view_search.findViewById(R.id.end);

                    initDatePicker();

                    view_search.findViewById(R.id.down_start).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
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

                    view_search.findViewById(R.id.down_end).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
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

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
//        currentDateStart.setText(now.split(" ")[0]);
//        currentDateEnd.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateStart.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDateEnd.setText(time.split(" ")[0]);
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
                }
            }, currentDateStart.getText().toString() + " 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
            customDatePicker2.showSpecificTime(false); // 不显示时和分
            customDatePicker2.setIsLoop(false); // 允许循环滚动

            customDatePicker2.show(currentDateStart.getText().toString());
        }
    }

}
