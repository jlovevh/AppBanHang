package com.tvt.projectcuoikhoa.model;

public class Cart {

    private int id_sp;
    private String url_image;
    private int price;
    private String name;
    private int id_user;
    private int soluong;


    public Cart() {
    }

    public Cart(int id_sp, String url_image, int price, String name, int id_user, int soluong) {

        this.id_sp = id_sp;
        this.url_image = url_image;
        this.price = price;
        this.name = name;
        this.id_user = id_user;
        this.soluong = soluong;
    }


    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
