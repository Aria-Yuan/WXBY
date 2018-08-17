package com.example.joan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class judgeimformation_judgeReason extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgeimformation);

        ListView listView=(ListView)findViewById(R.id.pastcases);
    }


}
