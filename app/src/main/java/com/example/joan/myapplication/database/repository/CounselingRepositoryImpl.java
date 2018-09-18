package com.example.joan.myapplication.database.repository;

import com.example.joan.myapplication.database.model.LawFirmModel;
import com.example.joan.myapplication.database.model.LegalCounselingModel;
import com.example.joan.myapplication.database.model.CounselingModel;
import com.example.joan.myapplication.database.model.ResponseModel;
import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CounselingRepositoryImpl {

    public List<LegalCounselingModel> convert(JSONArray s){
        List<LegalCounselingModel> counselings = new ArrayList<LegalCounselingModel>();
        for (int i = 0 ; i < s.size(); i++) {
            try {
                JSONObject a = s.getJSONObject(i);

                LegalCounselingModel counseling = new LegalCounselingModel();
//                counseling.setLawyer(a.getString(""));
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                ParsePosition pos = new ParsePosition(0);
//                Date createTime = formatter.parse(a.getString("create_time"), pos);
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
}
