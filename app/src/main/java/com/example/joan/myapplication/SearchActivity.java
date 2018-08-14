package com.example.joan.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        TextView searchBox = (TextView) findViewById(R.id.page_search);
        Drawable d = ContextCompat.getDrawable(SearchActivity.this,R.drawable.search);
        d.setBounds(-15, 0, 70, 70);
        searchBox.setCompoundDrawables(d,null,null,null);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                onDestroy();
            }
        });
    }

}
