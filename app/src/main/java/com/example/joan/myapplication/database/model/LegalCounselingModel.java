package com.example.joan.myapplication.database.model;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LegalCounselingModel implements Serializable{
    private String id;
    private RegisterModel questioner;
    private LawyerModel lawyer;
    private String createTime;
    private int viewCount;
    private List<CounselingModel> content;
    private int publishFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CounselingModel> getContent() {
        return content;
    }

    public void setContent(List<CounselingModel> content) {
        this.content = content;
    }

    public int getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(int publishFlag) {
        this.publishFlag = publishFlag;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
