package com.example.joan.database.repository;

import com.example.joan.database.model.CaseConsultModel;

import java.util.List;

public interface CaseConsultRepository {

    //检索所有文档
    List<CaseConsultModel> findAll();

    //以id检索文档
    CaseConsultModel findById(String code);

    //以关键字检索
    List<CaseConsultModel> findByCondition(String keyWord);


}
