package com.example.joan.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.myapplication.database.model.CounselingModel;

public class CounselingQuestionView extends LinearLayout {

    //正文
    private TextView content;

    //時間
    private TextView time;


    public CounselingQuestionView(Context context) {
        super(context);
    }

    public CounselingQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 初始化各个控件
     */
    public CounselingQuestionView init(){
        LayoutInflater.from(getContext()).inflate(R.layout.counseing_question_content_layout_questioner, this, true);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        return this;
    }

    /**
     * 律所名称+地址+标签
     *
     * @return
     */
    public CounselingQuestionView init(String content, String time) {
        init();
        setContent(content);
        setTime(time);

        return this;
    }

    public CounselingQuestionView setContent(String x){
        content.setText(x);
        return  this;
    }

    public CounselingQuestionView setTime(String x){
        time.setText(x);
        return  this;
    }

}
