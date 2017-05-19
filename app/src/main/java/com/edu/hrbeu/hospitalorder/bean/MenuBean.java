package com.edu.hrbeu.hospitalorder.bean;


public class MenuBean {
    private String status;
    private String tip;
    private Menu menu;



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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public class Menu{
        private int menu_id;
        private String menu_name;
        private String menu_url;
        private String menu_intro;
        private String menu_price;
        private String menu_class;

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

        public String getMenu_intro() {
            return menu_intro;
        }

        public void setMenu_intro(String menu_intro) {
            this.menu_intro = menu_intro;
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
}
