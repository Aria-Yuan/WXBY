package com.example.joan.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joan.myapplication.R;

public class MainSearchResultCounsel extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        initView();

        return inflater.inflate(R.layout.activity_main_search_result_counsel, container, false);
    }

    public void initView() {



    }
}
