package com.edu.hrbeu.hospitalorder.bean;



public class Menu {
    private int menu_id;
    private String menu_name;
    private String menu_url;
    private String menu_introduce;
    private String menu_price;
    private String menu_class;

    public Menu(int menu_id, String menu_name, String menu_url, String menu_introduce, String menu_price, String menu_class) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.menu_url = menu_url;
        this.menu_introduce = menu_introduce;
        this.menu_price = menu_price;
        this.menu_class = menu_class;
    }

    public Menu() {

    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getMenu_introduce() {
        return menu_introduce;
    }

    public void setMenu_introduce(String menu_introduce) {
        this.menu_introduce = menu_introduce;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_class() {
        return menu_class;
    }

    public void setMenu_class(String menu_class) {
        this.menu_class = menu_class;
    }
}
