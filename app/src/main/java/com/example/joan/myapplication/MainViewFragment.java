package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainViewFragment extends BaseFragment {
    String position;
    public static final String TITLE_TAG = "tabTitle";

    public static MainViewFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();

        MainViewFragment fragment = new MainViewFragment();
        args.putString(TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        position = getArguments().getString("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_puicture, container, false);
//        TextView tv = view.findViewById(R.id.tv_title);
//        if (getArguments() != null) tv.setText(getArguments().getString(TITLE_TAG));

        return view;
    }

}
