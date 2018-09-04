package com.example.joan.myapplication.database.repository;

import com.example.joan.myapplication.database.MongoDBUtil;
import com.example.joan.myapplication.database.model.LawyerModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LawyerRepositoryImpl implements LawyerRepository {
    MongoDBUtil mongoDb = new MongoDBUtil("wxby");
    MongoCollection<Document> collection = mongoDb.getCollection("lawyer");

    //添加文档
    public void create(LawyerModel user){
        Document lawyer = new Document();
        lawyer.append("reg_id", user.getRegMsg());
        lawyer.append("name", user.getName());
        lawyer.append("job", user.getName());
        lawyer.append("education", user.getEducation());
        lawyer.append("experience", user.getExperience());
        lawyer.append("description", user.getDescription());
        lawyer.append("price", user.getPrice());
        lawyer.append("counseling",new ArrayList<ObjectId>());
        lawyer.append("comment", null);
        collection.insertOne(lawyer);
    }

    //更新文档
    public void update(LawyerModel user){
        Document lawyerOld = new Document("id", user.getId());
        Document lawyerNew = new Document();
        lawyerNew.append("name", user.getName());
        lawyerNew.append("job", user.getName());
        lawyerNew.append("education", user.getEducation());
        lawyerNew.append("experience", user.getExperience());
        lawyerNew.append("description", user.getDescription());
        lawyerNew.append("price", user.getPrice());
        Document lawyerNewNewSet = new Document("$set", lawyerNew);
        collection.updateOne(lawyerOld,lawyerNewNewSet);
    }

    //检索所有文档
    public List<LawyerModel> findAll(){
        MongoCursor<Document> cursor = collection.find().limit(50).iterator();
        List<LawyerModel> lawyerList = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                LawyerModel lawyer = new LawyerModel();
                Document current_cursor = cursor.next();
                lawyer.setId(current_cursor.getObjectId("_id"));
                lawyer.setRegMsg(current_cursor.getObjectId("reg_id"));
                lawyer.setName(current_cursor.getString("name"));
                lawyer.setJob(current_cursor.getString("job"));
                lawyer.setEducation(current_cursor.getString("education"));
                lawyer.setExperience(current_cursor.getString("experience"));
                lawyer.setDescription(current_cursor.getString("description"));
                lawyer.setPrice(current_cursor.getDouble("price"));
                lawyer.setCounselingList(current_cursor.get("counseling_list",new ArrayList<ObjectId>()));
                lawyer.setComment(current_cursor.getDouble("comment"));

                lawyerList.add(lawyer);
            }
        } finally {
            cursor.close();
        }
        return lawyerList;
    }

    //有条件的检索文档
    private List<LawyerModel> find(Document condition){
        MongoCursor<Document> cursor = collection.find(condition).limit(50).iterator();
        List<LawyerModel> lawyerList = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                LawyerModel lawyer = new LawyerModel();
                Document current_cursor = cursor.next();
                lawyer.setId(current_cursor.getObjectId("_id"));
                lawyer.setRegMsg(current_cursor.getObjectId("reg_id"));
                lawyer.setName(current_cursor.getString("name"));
                lawyer.setJob(current_cursor.getString("job"));
                lawyer.setEducation(current_cursor.getString("education"));
                lawyer.setExperience(current_cursor.getString("experience"));
                lawyer.setDescription(current_cursor.getString("description"));
                lawyer.setPrice(current_cursor.getDouble("price"));
                lawyer.setCounselingList(current_cursor.get("counseling_list",new ArrayList<ObjectId>()));
                lawyer.setComment(current_cursor.getDouble("comment"));

                lawyerList.add(lawyer);
            }
        } finally {
            cursor.close();
        }
        return lawyerList;
    }

    //以id检索文档
    public LawyerModel findById(ObjectId code){
        Document condition = new Document();
        condition.append("_id", code);
        return find(condition).get(0);
    }

    //以关键字检索
    public List<LawyerModel> findByCondition(String keyWord){
        List<Document> condition = new ArrayList<>();
        //设置正则表达
        Pattern regular = Pattern.compile("(?i)" + keyWord + ".*$", Pattern.MULTILINE);
        condition.add(new Document("name" , regular));
        condition.add(new Document("job" , regular));
        condition.add(new Document("description" , regular));
        return find(new Document("$or",condition));
    }

}
