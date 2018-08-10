package com.example.joan.database.repository;

import com.example.joan.database.model.JudgementModel;
import com.example.joan.database.MongoDBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JudgementRepositoryImpl implements JudgementRepository{
    MongoDBUtil mongoDb = new MongoDBUtil("wxbyt");
    MongoCollection<Document> collection = mongoDb.getCollection("judgement");

    //检索所有文档
    public List<JudgementModel> findAll()
    {
        MongoCursor<Document> cursor = collection.find().limit(50).iterator();
        List<JudgementModel> judgementList = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                JudgementModel judgement = new JudgementModel();
                Document current_cursor = cursor.next();
                judgement.setId(current_cursor.getObjectId("_id"));
                judgement.setjId(current_cursor.getString("j_id"));
                judgement.setjDate(current_cursor.getString("j_date"));
                judgement.setjReason(current_cursor.getString("j_reason"));
                judgement.setjContent(current_cursor.getString("j_content"));
                judgement.setjRelavent(current_cursor.get("j_relevant",new ArrayList<Document>()));
                judgement.setjPrevious(current_cursor.get("j_previous",new ArrayList<Document>()));
                judgement.setjLaws(current_cursor.getString("j_laws"));
                judgementList.add(judgement);
            }
        } finally {
            cursor.close();
        }
        return judgementList;
    }

    //有条件的检索文档
    private List<JudgementModel> find(Document condition)
    {
        MongoCursor<Document> cursor = collection.find(condition).limit(50).iterator();
        List<JudgementModel> judgementList = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                JudgementModel judgement = new JudgementModel();
                Document current_cursor = cursor.next();
                judgement.setId(current_cursor.getObjectId("_id"));
                judgement.setjId(current_cursor.getString("j_id"));
                judgement.setjDate(current_cursor.getString("j_date"));
                judgement.setjReason(current_cursor.getString("j_reason"));
                judgement.setjContent(current_cursor.getString("j_content"));
                judgement.setjRelavent(current_cursor.get("j_relevant",new ArrayList<Document>()));
                judgement.setjPrevious(current_cursor.get("j_previous",new ArrayList<Document>()));
                judgement.setjLaws(current_cursor.getString("j_laws"));
                judgementList.add(judgement);
            }
        } finally {
            cursor.close();
        }
        return judgementList;
    }

    //以id检索文档
    public List<JudgementModel> findById(ObjectId code) {
        Document condition = new Document();
        condition.append("_id", code);
        return find(condition);
    }

    //以关键字检索
    public  List<JudgementModel> findByCondition(String keyWord)
    {
        List<Document> condition = new ArrayList<>();
        //设置正则表达
        Pattern regular = Pattern.compile("(?i)" + keyWord + ".*$", Pattern.MULTILINE);
        condition.add(new Document("j_reason" , regular));
        condition.add(new Document("j_content" , regular));
        condition.add(new Document("j_laws" , regular));
        return find(new Document("$or",condition));
    }



}
