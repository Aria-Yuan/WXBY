package com.example.joan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.myapplication.oneLineView.HomeNewsLayout;

import net.sf.json.JSONArray;

public class SearchResultMoreNewsActivity extends AppCompatActivity {

    private JSONArray jsonArray;
    private String keyword;
    private String[] titlePart = {"熱門新聞", "名家評論"};
    private int type;
    private JSONArray newsModels;
    private JSONArray commentModels;
    private LinearLayout ll;
    private Button back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_more_news);

        getData();
        initBase();
        initView();

    }

    private void getData() {

        Intent intent = getIntent();
        jsonArray = JSONArray.fromObject(intent.getStringExtra("data"));
        type = intent.getIntExtra("type", 0);
        keyword = intent.getStringExtra("keyword");
        if (type == 0)
            newsModels = jsonArray;
        else
            commentModels = jsonArray;

    }

    private void initBase() {

        ll = findViewById(R.id.search_result_more_news_list);
        back = findViewById(R.id.search_result_more_news_back);
        title = findViewById(R.id.search_result_more_news_text);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title.setText(keyword + " 的" + titlePart[type] + "搜尋結果");
    }

    private void initView() {

        if (type == 0)
            setOneNewsView(newsModels.size());
        else
            setOneCommentView(commentModels.size());

    }

    private void setOneNewsView(int num){
        for (int i = 0; i < num; i++) {
            String img;
            if (newsModels.getJSONObject(i).getJSONArray("src").size() > 0) {
                img = newsModels.getJSONObject(i).getJSONArray("src").getString(0);
            } else {
                img = "https://i.imgur.com/qM6pytU.jpg";
            }

            ll.addView(new HomeNewsLayout(SearchResultMoreNewsActivity.this)
//                          .init());
                    .initNews(newsModels.getJSONObject(i).getString("title").replace("／", " | "), newsModels.getJSONObject(i).getString("article").replaceAll("\n", ""), img));
        }
    }

    private void setOneCommentView(int num){
        for (int i = 0; i < num; i++) {
            String img;
            if (commentModels.getJSONObject(i).getJSONArray("src").size() > 0) {
                img = commentModels.getJSONObject(i).getJSONArray("src").getString(0);
            } else {
                img = "https://i.imgur.com/qM6pytU.jpg";
            }

            ll.addView(new HomeNewsLayout(SearchResultMoreNewsActivity.this)
//                          .init());
                    .initNews(commentModels.getJSONObject(i).getString("title").replace("／", " | "), commentModels.getJSONObject(i).getString("article").replaceAll("\n", ""), img));
        }
    }


}
