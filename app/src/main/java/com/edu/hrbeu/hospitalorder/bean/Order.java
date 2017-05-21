package com.edu.hrbeu.hospitalorder.bean;



public class Order {
    private String order_user_name;
    private String order_menu_name;
    private String order_menu_price;
    private String order_num;
    private String order_time;

    public String getOrder_user_name() {
        return order_user_name;
    }

    public void setOrder_user_name(String order_user_name) {
        this.order_user_name = order_user_name;
    }

    public String getOrder_menu_name() {
        return order_menu_name;
    }

    public void setOrder_menu_name(String order_menu_name) {
        this.order_menu_name = order_menu_name;
    }

    public String getOrder_menu_price() {
        return order_menu_price;
    }

    public void setOrder_menu_price(String order_menu_price) {
        this.order_menu_price = order_menu_price;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}
