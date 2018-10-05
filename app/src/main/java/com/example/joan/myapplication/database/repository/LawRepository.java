package com.example.joan.myapplication.database.repository;

import com.example.joan.myapplication.database.model.LawModel;

import java.util.List;

public interface LawRepository {
    //检索所有文档
    List<LawModel> findAll();

    //以id检索文档
    LawModel findById(String code);

    //以关键字检索
    List<LawModel> findByCondition(String keyWord);

}
