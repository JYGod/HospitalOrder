package com.edu.hrbeu.hospitalorder.bean;


import java.util.ArrayList;

public class ResultBean {
    private String status;
    private ArrayList<Menu> list;
    private String tip;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Menu> getList() {
        return list;
    }

    public void setList(ArrayList<Menu> list) {
        this.list = list;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
