package com.example.joan.database.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class LegalCounselingModel {
    private ObjectId id;
    private RegisterModel questioner;
    private LawyerModel lawyer;
    private Date createTime;
    private List<counselingModel> content;
    private int publishFlag;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public RegisterModel getQuestioner() {
        return questioner;
    }

    public void setQuestioner(RegisterModel questioner) {
        this.questioner = questioner;
    }

    public LawyerModel getLawyer() {
        return lawyer;
    }

    public void setLawyer(LawyerModel lawyer) {
        this.lawyer = lawyer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<counselingModel> getContent() {
        return content;
    }

    public void setContent(List<counselingModel> content) {
        this.content = content;
    }

    public int getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(int publishFlag) {
        this.publishFlag = publishFlag;
    }
}
