package com.example.joan.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CaseConsultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next, back;//, adConfirm, adCancel;
    private EditText text;
    private AlertDialog finished;
    private int length = 14, state;
    private boolean isSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_consult);

        initItems();
    }

    private void initItems(){

        next = findViewById(R.id.case_consult_next);
        back = findViewById(R.id.case_consult_back);
        text = findViewById(R.id.case_consult_text);

        setListener();

//        System.out.println("Init successful");
    }

    private void setListener(){

        back.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    private boolean isEnoughLength(){
        return text.getText().length() > length ? true : false;
    }

    private boolean isEmpty(){
        return text.getText().length() == 0 ? true : false;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.case_consult_next:
                setNext();
                break;
            case R.id.case_consult_back:
                goBack();
                break;
        }
    }


    private void setNext() {
        if (isEnoughLength()) {
            submit();
            if (isSubmitted) {
                state = success();
            } else {
                state = failed();
            }
        }else{
            state = setNotEnough();
        }
        afterCreateDialog(state);
    }

    private int setNotEnough() {
        finished = new AlertDialog.Builder(this)
                .setTitle("字數不夠喔~")//设置对话框的标题
                .setMessage("最少字數需要" + length + "才可以提交！")//设置对话框的内容
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finished.cancel();
                    }
                })
                .create();
        return -2;
    }

    private int failed() {
        autoSave();
        finished = new AlertDialog.Builder(this)
                .setTitle("您的咨詢信息")//设置对话框的标题
                .setMessage("送出失敗了誒~")//设置对话框的内容
                .setPositiveButton("重試", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        submit();
                    }
                })
                .setNegativeButton("再看看~", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finished.cancel();
                    }
                })
                .create();
        return -1;
    }

    private int success() {
        finished = new AlertDialog.Builder(this)
                .setTitle("您的咨詢信息")//设置对话框的标题
                .setMessage("已經成功送出咯~")//设置对话框的内容
                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CaseConsultActivity.this, CaseConsultResultActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right, R.anim.left);
                    }
                })
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create();
        return 0;
    }

    private int confirmBack(){
        finished = new AlertDialog.Builder(this)
                .setTitle("確定要退出嗎")//设置对话框的标题
                .setMessage("距離獲得幫助已經不遠了！現在退出您已經輸入的信息可能會丟失喔！")//设置对话框的内容
                .setPositiveButton("確定退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        overridePendingTransition(R.anim.left, R.anim.left_exit);
                    }
                })
                .setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finished.cancel();
                    }
                })
                .create();
        return 1;
    }

    private void submit() {//提交
        isSubmitted = true;
        return;
    }


    private void afterCreateDialog(int state) {

        finished.show();
        switch (state){
            case 1:
                Button back, cancel;
                back = finished.getButton(DialogInterface.BUTTON_POSITIVE);
                cancel = finished.getButton(DialogInterface.BUTTON_NEGATIVE);
                back.setTextColor(getResources().getColor(R.color.selector_item_color));
                cancel.setTextColor(getResources().getColor(R.color.selector_item_color));
                break;
            case 0:
            case -1:
                Button no, next;
                next = finished.getButton(DialogInterface.BUTTON_POSITIVE);
                no = finished.getButton(DialogInterface.BUTTON_NEGATIVE);
                next.setTextColor(getResources().getColor(R.color.selector_item_color));
                no.setTextColor(getResources().getColor(R.color.selector_item_color));
                break;
            case -2:
                Button confirm = finished.getButton(DialogInterface.BUTTON_POSITIVE);;
                confirm.setTextColor(getResources().getColor(R.color.selector_item_color));
                break;
        }

    }

    private void goBack() {

        if (isEmpty()){
            finish();
            overridePendingTransition(R.anim.left, R.anim.left_exit);
        }else{
            autoSave();
            state = confirmBack();
            finished.show();
            afterCreateDialog(state);
        }
    }

    private void autoSave() {
    }

}
