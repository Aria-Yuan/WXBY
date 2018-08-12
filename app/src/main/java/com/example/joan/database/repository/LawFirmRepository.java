package com.example.joan.database.repository;

import com.example.joan.database.model.LawFirmModel;

import org.bson.types.ObjectId;

import java.util.List;

public interface LawFirmRepository {
    //检索所有文档
    List<LawFirmModel> findAll();

    //以id检索文档
    LawFirmModel findById(ObjectId code);

    //以关键字检索
    List<LawFirmModel> findByCondition(String keyWord);
}
