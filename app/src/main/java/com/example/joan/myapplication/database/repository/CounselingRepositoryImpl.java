package com.example.joan.myapplication.database.repository;

import com.example.joan.myapplication.database.model.BaseModel;
import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.model.LawyerModel;
import com.example.joan.myapplication.database.model.LegalCounselingModel;
import com.example.joan.myapplication.database.model.CounselingModel;
import com.example.joan.myapplication.database.model.ResponseModel;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CounselingRepositoryImpl {

    public List<LegalCounselingModel> convert(JSONArray s){
        List<LegalCounselingModel> counselings = new ArrayList<LegalCounselingModel>();
        for (int i = 0 ; i < s.size(); i++) {
            try {
                JSONObject a = s.getJSONObject(i);

                final LegalCounselingModel counseling = new LegalCounselingModel();
//                counseling.setLawyer(a.getString(""));

                try{
                    RequestParams params = new RequestParams("http://" + BaseModel.IP_ADDR +":8080/searchLawyer.action");
                    params.addQueryStringParameter("condition",a.getString("lawyer"));
//            params.addQueryStringParameter("condition","吕浩然觉得不用写");
                    params.addQueryStringParameter("type","1");
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            JSONArray jArray= JSONArray.fromObject(s);
                            LawyerModel l = new LawyerRepositoryImpl().convert(jArray).get(0);
                            counseling.setLawyer(l);
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

                counseling.setId(a.getString("_id"));
                counseling.setCreateTime(a.getString("create_time").replace("T", " "));
                counseling.setViewCount(a.getInt("view_count"));

                //问答列表
                JSONArray content = a.getJSONArray("content");
                System.out.println(content);
                List<CounselingModel> contentList = new ArrayList<>();
                //封装每次提问
                for(int j = 0; j < content.size(); j++){
                    JSONObject c = content.getJSONObject(j);
                    CounselingModel ccontent = new CounselingModel();
//                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    ParsePosition pos1 = new ParsePosition(0);
//                    Date createTime1 = formatter.parse(c.getString("create_time"), pos);
                    ccontent.setCreate_time(c.getString("create_time").replace("T", " "));
                    ccontent.setQuestion(c.getString("question"));
                    List<ResponseModel> relies = new ArrayList<>();
                    JSONArray responses = c.getJSONArray("response");
                    //封装每次回答
                    for (int k = 0; k < responses.size(); k++){
                        JSONObject response = responses.getJSONObject(k);
                        ResponseModel rm = new ResponseModel();
                        rm.setDate(response.getString("time").replace("T", " "));
                        rm.setContent(response.getString("content"));
                        relies.add(rm);
                    }
                    ccontent.setResponse(relies);
                    contentList.add(ccontent);
                }

                counseling.setContent(contentList);
                counselings.add(counseling);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return counselings;
    }

    public String disconvert(LegalCounselingModel l){
        Document counseling = new Document();
        counseling.append("_id",l.getId());
        counseling.append("view_count",l.getViewCount());
        return counseling.toJson();
    }

    public String createNew(String question,String lawyer) throws Exception{
        Document counseling = new Document();
        //目前時間
        Date date = new Date();
        //設定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //進行轉換
        String dateString = sdf.format(date);
        counseling.append("questioner",lawyer);
        counseling.append("lawyer",lawyer);
        counseling.append("create_time",dateString);
        counseling.append("view_count",0);
        CounselingModel c = new CounselingModel();
        c.setQuestion(question);
        c.setCreate_time(dateString);
        List<Document> cs = new ArrayList<>();
        Document content = new Document();
        content.append("create_time",sdf.parse(c.getCreate_time()));
        content.append("question",c.getQuestion());
        content.append("response",new ArrayList<Document>());
        cs.add(content);
        counseling.append("content",cs);

        return counseling.toJson();
    }


}
