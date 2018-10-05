package com.example.joan.database.repository;

import java.util.List;

public interface QuickConsultRepository {

    //检索所有文档
    List<QuickConsultRepository> findAll();

    //以id检索文档
    QuickConsultRepository findById(String code);

    //以关键字检索
    List<QuickConsultRepository> findByCondition(String keyWord);

}
