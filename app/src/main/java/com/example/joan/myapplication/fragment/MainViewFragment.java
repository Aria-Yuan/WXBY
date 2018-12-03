package com.example.joan.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.joan.myapplication.database.model.BaseModel;
import com.example.joan.myapplication.database.model.NewsModel;
import com.example.joan.myapplication.oneLineView.HomeNewsLayout;
import com.example.joan.myapplication.R;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainViewFragment extends BaseFragment {
    private String position;
    private LinearLayout mainBody;
    private net.sf.json.JSONArray newsModels;
    private View view;
    public static final String TITLE_TAG = "tabTitle";

    public static MainViewFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();

        MainViewFragment fragment = new MainViewFragment();
        args.putString(TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        position = getArguments().getString("position");
//        newsModels = (JSONArray) getArguments().getSerializable("news");
//        System.out.println( "^^^^^^^^^^^^^^^^^^^^^^^" + newsModels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.shouye_puicture, container, false);
//        TextView tv = view.findViewById(R.id.tv_title);
//        if (getArguments() != null) tv.setText(getArguments().getString(TITLE_TAG));

            searchCase();
        }
        return view;
    }

    public void initView(View view){
        mainBody = view.findViewById(R.id.main_body);
        try{
            for(int i = 0; i < newsModels.size(); i++){
                String img;
                if(newsModels.getJSONObject(i).getJSONArray("src").size() > 0){
                    img = newsModels.getJSONObject(i).getJSONArray("src").getString(0);
                }else{
                    img = "https://i.imgur.com/qM6pytU.jpg";
                }

                mainBody.addView(new HomeNewsLayout(getContext())
//                          .init());
                        .initNews(newsModels.getJSONObject(i).getString("title").replace("／"," | "), newsModels.getJSONObject(i).getString("article").replaceAll("\n",""), img));
            }
        }catch (Exception e){

        }

    }

    private void searchCase(){
        try{
            RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/getNews.action");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    System.out.println(s);
                    net.sf.json.JSONObject jArray= net.sf.json.JSONObject.fromObject(s);
//                    System.out.println(jArray);
                    newsModels = jArray.getJSONArray("hotNews");
//                    System.out.println("@@@@@@@@@@@" + hot + "^^^^^^^^^^^^^^^^^^^^^^^");
//                    comment = jArray.getJSONArray("comment");
                    initView(view);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
