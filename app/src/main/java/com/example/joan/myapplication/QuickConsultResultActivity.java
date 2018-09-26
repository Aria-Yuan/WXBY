package com.example.joan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joan.myapplication.database.model.QuickConsultModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bson.types.ObjectId;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class QuickConsultResultActivity extends AppCompatActivity {

//    private QuickConsultResultListComment comment;
//    private QuickConsultResultListReply reply;
//    private List<Fragment> list;
//    private ViewPager pager;
//    private TabLayout tabLayout;
    private LinearLayout ll;
    private QuickConsultModel data;
    private String id;
    private TextView content, name, time, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_consult_result);

        getData();
        initItems();

    }

    private void getData() {
        x.Ext.init(getApplication());
//        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
        id = "5b6eaad08d35692c10ea06d1";
        final int[] type = new int[1];

        try {
            RequestParams params = new RequestParams("http://169.254.219.229:8080/getQuickConsultResult.action");
            params.addQueryStringParameter("id", id);
            System.out.println(params.toString());
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(s);
                    type[0] = jsonObject.get("state").getAsInt();

                    if (type[0] == 1) {JsonObject jo = jsonObject.getAsJsonObject("data");

                        data.setAuthor(new ObjectId(jo.get("author").getAsString()));
                        data.setAuthor_name(jo.get("author_name").getAsString());
                        data.setContent(jo.get("content").getAsString());
//                        System.out.println("-----------------------");
//                        System.out.println(jo.get("create_time").toString());
                        data.setDate(jo.get("create_time").toString().replace("T", " "));
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        System.out.println("-----------------------");
//                        String d = jo.get("create_time").toString();
//                        System.out.println(d);
//                        try {
//                            data.setCreate_time(sdf.parse(d));
//                        } catch (ParseException e) {
//                            System.out.println("here!!!");
//                            data.setCreate_time(new Date());
//                        }

                        data.setId(new ObjectId(jo.get("_id").getAsString()));
                        data.setLike(jo.get("like").getAsInt());

                        JsonArray pictures = jo.getAsJsonArray("picture");
                        for (JsonElement picture: pictures){

                        }

                        JsonArray comments = jo.getAsJsonArray("lawyer_reply");
                        List<QuickConsultModel.Reply> replies = new ArrayList<>();
                        for (JsonElement comment: comments){
                            QuickConsultModel what = new QuickConsultModel();
                            QuickConsultModel.Reply reply = what.new Reply();
                            JsonObject temp = comment.getAsJsonObject();

                            System.out.println(temp);

//                            System.out.println(temp.get("author").getAsString());

                            reply.setAuthor(new ObjectId(temp.get("author").getAsString()));
                            reply.setAuthor_name(temp.get("author_name").getAsString());
                            reply.setDate((temp.get("create_time").toString().replace("T", " ")));
                            reply.setContent(temp.get("content").getAsString());
                            JsonArray ror = temp.getAsJsonArray("replies");
                            List<String> rsor = new ArrayList<>();
//                            int count = 0;
                            for (JsonElement r: ror){
                                String rr = r.getAsString();
                                rsor.add(rr);
//                                System.out.println("1111111111111111111");
                            }
                            reply.setReplies(rsor);
                            reply.setParent(new ObjectId(temp.get("parent").getAsString()));
                            reply.setId(new ObjectId(temp.get("reply_id").getAsString()));
                            reply.setLike(temp.get("like").getAsInt());
                            replies.add(reply);
                        }

//                        System.out.println("2222222222222222222222");

                        data.setLawyer_reply(replies);
                        data.setView_count(jo.get("view_count").getAsInt());
//                        System.out.println("print SomeThing!!!!!!!!!!!!!!!");
//                        System.out.println(data);

                        initMain();
                        initCommentList();

                    } else {
                        onError(new Throwable(), false);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                    System.out.println("OnError");

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initMain() {

        content = findViewById(R.id.quick_consult_result_context);
        name = findViewById(R.id.quick_consult_result_name);
        view = findViewById(R.id.quick_consult_result_view);
        time = findViewById(R.id.quick_consult_result_time);

        content.setText(data.getContent());
        name.setText(data.getAuthor_name());
        time.setText(data.getDate().replace("\"", ""));
        view.setText(String.valueOf(data.getView_count()) + getResources().getString(R.string.quick_consult_result_viewtime));


    }

    private void initItems() {

//        tabLayout = findViewById(R.id.quick_consult_result_tablayout);
//        pager = findViewById(R.id.quick_consult_result_pager);
//
//        comment = new QuickConsultResultListComment();
//        reply = new QuickConsultResultListReply();
//
//        list = new ArrayList<>();
//        list.add(comment);
//        list.add(reply);
//        pager.setAdapter(new CaseConsultAdapter(getSupportFragmentManager(),list));
//
//        initTabLayout();

        ll = findViewById(R.id.quick_consult_result_comment_list);
        data = new QuickConsultModel();



    }

    private void initCommentList() {

        for (QuickConsultModel.Reply reply: data.getLawyer_reply()){

            View view = View.inflate(this, R.layout.sample_quick_consult_single_comment, null);
            Button image, like, comment;
            TextView content, time, replies, favorites, name;

//            image = view.findViewById(R.id.quick_consult_result_single_user_image);
            like = view.findViewById(R.id.quick_consult_result_single_like);
            comment = view.findViewById(R.id.quick_consult_result_single_reply);

            content = view.findViewById(R.id.quick_consult_result_single_content);
            time = view.findViewById(R.id.quick_consult_result_single_time);
            replies = view.findViewById(R.id.quick_consult_result_single_replies);
            favorites = view.findViewById(R.id.quick_consult_result_single_favorites);
            name = view.findViewById(R.id.quick_consult_result_single_user_name);
            content.setText(reply.getContent());
            time.setText(reply.getDate());
            replies.setText(reply.getReplies().size() + " 條回復");
            favorites.setText(String.valueOf(reply.getLike()));
            name.setText(reply.getAuthor_name());
            System.out.println("111111111111111111111111111111111111111111111");

            replies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            ll.addView(view);

        }

    }

//    private void initTabLayout() {
//
//        tabLayout.setupWithViewPager(pager);
//        tabLayout.removeAllTabs();
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.quick_consult_result_reply));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.quick_consult_result_comment));

//        reply.temp();
//        comment.temp();

//    }
}
