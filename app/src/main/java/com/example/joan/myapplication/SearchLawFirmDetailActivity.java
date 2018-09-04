package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SearchLawFirmDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.law_firm_detail);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.like:{

            }break;

            case R.id.follow:{

            }break;

            case R.id.contact:{

            }break;
        }
    }
}
