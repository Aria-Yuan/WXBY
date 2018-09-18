package com.example.joan.myapplication;

public class Sort {

    private String name;
    private String detail;
    private String himage;

    public Sort(){

    }

    public Sort(String name, String detail){
        this.name = name;
        this.detail = detail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage;
    }
}
