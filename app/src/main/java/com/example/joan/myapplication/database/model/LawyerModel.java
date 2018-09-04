package com.example.joan.myapplication.database.model;

import org.bson.types.ObjectId;

import java.util.List;

public class LawyerModel {
    private ObjectId id;
    private ObjectId regMsg;
    private String name;
    private String job;
    private String education;
    private String experience;
    private String description;
    private double price;
    private List<ObjectId> counselingList;
    private double comment;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getRegMsg() {
        return regMsg;
    }

    public void setRegMsg(ObjectId regMsg) {
        this.regMsg = regMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ObjectId> getCounselingList() {
        return counselingList;
    }

    public void setCounselingList(List<ObjectId> counselingList) {
        this.counselingList = counselingList;
    }

    public double getComment() {
        return comment;
    }

    public void setComment(double comment) {
        this.comment = comment;
    }
}
