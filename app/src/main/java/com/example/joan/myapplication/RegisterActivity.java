package com.example.joan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initItems();

    }

    private void initItems() {

        back = findViewById(R.id.register_back);
        register = findViewById(R.id.register_register);
        
        register.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.register_back:

                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;
                
            case R.id.register_register:
                
                registerNewAccount();

        }

    }

    private void registerNewAccount() {

    }
}
