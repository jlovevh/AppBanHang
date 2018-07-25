package com.tvt.projectcuoikhoa.model;

public class UserFB {

    private int id;
    private String fb_id, name, email, url;

    public UserFB() {
    }

    public UserFB(String fb_id, String name, String email, String url) {
        this.fb_id = fb_id;
        this.name = name;
        this.email = email;
        this.url = url;
    }

    public UserFB(int id, String fb_id, String name, String email, String url) {
        this.id = id;
        this.fb_id = fb_id;
        this.name = name;
        this.email = email;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
