package com.example.joan.myapplication;

public class Lawyer {

    private String branch;
    private String fee;
    private String special;
    private String response;
    private String identity;
    private String lname;
    private String rate;
    private String himage;
    private String id;
    private String record;
    private int year;
    private int times;
    private String personality;
    private String scholar;

    public Lawyer(){

    }

    public Lawyer(String id, String name, String identity, String branch, String special, String response,
                  String fee, String rate, String image){
        this.setId(id);
        this.lname = name;
        this.branch = branch;
        this.special = special;
        this.response = response;
        this.rate = rate;
        this.identity = identity;
        this.fee = fee;
        this.himage = image;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String responce) {
        this.response = responce;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String recurd) {
        this.record = recurd;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getScholar() {
        return scholar;
    }

    public void setScholar(String scholar) {
        this.scholar = scholar;
    }
}
