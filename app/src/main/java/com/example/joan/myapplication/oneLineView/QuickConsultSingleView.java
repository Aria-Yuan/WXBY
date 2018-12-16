package com.example.joan.myapplication.oneLineView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.myapplication.R;

public class QuickConsultSingleView extends LinearLayout {

    private TextView content, time,views, reply;

    private ImageView image;

    public QuickConsultSingleView(Context context) {
        super(context);
    }

    public QuickConsultSingleView init(){

        LayoutInflater.from(getContext()).inflate(R.layout.sample_quick_consult_single, this, true);
        content = findViewById(R.id.quick_consult_single_content);
        time = findViewById(R.id.quick_consult_single_time);
        reply = findViewById(R.id.quick_consult_single_reply);



        return this;

    }

}
