package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerQc {

    @SerializedName("idBannerqc")
    @Expose
    private String idBannerqc;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("urlBanner")
    @Expose
    private String urlBanner;
    @SerializedName("createAt")
    @Expose
    private String createAt;

    public String getIdBannerqc() {
        return idBannerqc;
    }

    public void setIdBannerqc(String idBannerqc) {
        this.idBannerqc = idBannerqc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlBanner() {
        return urlBanner;
    }

    public void setUrlBanner(String urlBanner) {
        this.urlBanner = urlBanner;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

}