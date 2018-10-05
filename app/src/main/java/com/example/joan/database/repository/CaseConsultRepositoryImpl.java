package com.example.joan.database.repository;

import com.example.joan.database.MongoDBUtil;
import com.example.joan.database.model.CaseConsultModel;
import com.example.joan.database.model.JudgementModel;
import com.example.joan.database.model.LawModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CaseConsultRepositoryImpl implements CaseConsultRepository {
    MongoDBUtil mongoDb = new MongoDBUtil("wxby");
    MongoCollection<Document> collection = mongoDb.getCollection("case_consult");
    @Override
    public List<CaseConsultModel> findAll() {
        return null;
    }

    @Override
    public CaseConsultModel findById(String code) {
        Document condition = new Document();
        condition.append("case_id", code);
        return find(condition).get(0);
    }

    private List<CaseConsultModel> find(Document condition)
    {
//        System.out.println(condition.get("_id"));
        MongoCursor<Document> cursor = collection.find(condition).iterator();
        List<CaseConsultModel> courtList = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                CaseConsultModel caseConsult = new CaseConsultModel();
                Document current_cursor = cursor.next();

                caseConsult.setId(current_cursor.getObjectId("_id"));
                caseConsult.setContent(current_cursor.getString("content"));
                caseConsult.setResult(current_cursor.getString("result"));
                caseConsult.setSimilar(current_cursor.get("similar", new ArrayList<Document>()));
                caseConsult.setRefer(current_cursor.get("refer", new ArrayList<Document>()));
                caseConsult.setState(current_cursor.getInteger("state"));

//                caseConsult.setJudgementModels(getJudgements(caseConsult.getSimilar()));
//                caseConsult.setLawModels(getLaws(caseConsult.getSimilar()));

                courtList.add(caseConsult);
            }
        } finally {
            cursor.close();
        }
        return courtList;
    }

    @Override
    public List<CaseConsultModel> findByCondition(String keyWord) {
        return null;
    }

    public List<JudgementModel> getJudgements(List<Document> idList){
        List<JudgementModel> resultList = new ArrayList<>();

        int count = 0;

        for(Document id: idList){

            resultList.add(new JudgementRepositoryImpl().findById(String.valueOf(idList.get(count))));
            count ++;

        }

        return resultList;
    }


    public List<LawModel> getLaws(List<Document> idList){
        List<LawModel> resultList = new ArrayList<>();

        int count = 0;

        for(Document id: idList){

            resultList.add(new LawRepositoryImpl().findById(String.valueOf(idList.get(count))));
            count ++;

        }

        return resultList;
    }
}
