package com.example.joan.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CounselingAnswerView extends LinearLayout {

    //律師頭像
    private ImageView picture;

    //律師姓名
    private  TextView name;

    //正文
    private TextView content;

    //時間
    private TextView time;


    public CounselingAnswerView(Context context) {
        super(context);
    }

    public CounselingAnswerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 初始化各个控件
     */
    public CounselingAnswerView init(){
        LayoutInflater.from(getContext()).inflate(R.layout.counseling_question_content_layout_lawyer, this, true);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        picture = findViewById(R.id.picture);
        name = findViewById(R.id.name);
        return this;
    }

    /**
     * 律所名称+地址+标签
     *
     * @return
     */
    public CounselingAnswerView init(String content, String time, String lawyerName) {
        init();
        name.setText(lawyerName);
        setConent(content);
        setTime(time);

        return this;
    }

    public CounselingAnswerView setConent(String x){
        content.setText(x);
        return  this;
    }

    public CounselingAnswerView setTime(String x){
        time.setText(x);
        return  this;
    }

}
