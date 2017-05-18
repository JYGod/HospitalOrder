package com.edu.hrbeu.hospitalorder.bean;


import java.util.ArrayList;

public class LikeBean {
    private String status;
    private String tip;
    private ArrayList<List>list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    public static class List{
        private int collect_id;
        private String collect_user_name;
        private String collect_menu_name;
        private String collect_time;

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }

        public String getCollect_user_name() {
            return collect_user_name;
        }

        public void setCollect_user_name(String collect_user_name) {
            this.collect_user_name = collect_user_name;
        }

        public String getCollect_menu_name() {
            return collect_menu_name;
        }

        public void setCollect_menu_name(String collect_menu_name) {
            this.collect_menu_name = collect_menu_name;
        }

        public String getCollect_time() {
            return collect_time;
        }

        public void setCollect_time(String collect_time) {
            this.collect_time = collect_time;
        }
    }
}
