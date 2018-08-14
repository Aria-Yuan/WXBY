package com.example.joan.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BaseFragment extends Fragment {
    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, container, false);
        //TextView textView = view.findViewById(R.id.tv_title);
        //textView.setText(title);
        setIconSize(view);

        TextView search = (TextView) view.findViewById(R.id.edt_search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchActivity.class); //设置跳转的Activity
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    private void setIconSize(View v){
//        Button mNextButton=(Button)v.findViewById(R.id.search_lawyer);
//        Drawable drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_lawyer);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.search_judgement);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_judgement);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.search_law_firm);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_firm);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.search_law);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_law);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.quick_counseling);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_counseling);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.unscramble);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_unscramble);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.schedule);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_schedule);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);
//
//        mNextButton=(Button)v.findViewById(R.id.help);
//        drawable=ContextCompat.getDrawable(getContext(),R.drawable.main_menu_help);
//        drawable.setBounds(0,0,180,180);
//        mNextButton.setCompoundDrawables(null,drawable,null,null);

        TextView searchBox = (TextView) v.findViewById(R.id.edt_search);
        Drawable d = ContextCompat.getDrawable(getContext(),R.drawable.search);
        d.setBounds(0, 5, 65, 65);
        searchBox.setCompoundDrawables(d,null,null,null);
    }
}