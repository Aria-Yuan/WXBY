package com.example.joan.database.repository;

import com.example.joan.database.model.QuickResponseModel;

public interface QuickResponseRepository {
    //新建问答
    void create(QuickResponseModel quickResponse);

    //增加回复
    void update(QuickResponseModel quickResponse);



}
