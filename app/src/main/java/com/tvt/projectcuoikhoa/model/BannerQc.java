package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerQc {

    @SerializedName("id_bannerqc")
    @Expose
    private Integer idBannerqc;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url_banner")
    @Expose
    private String urlBanner;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public Integer getIdBannerqc() {
        return idBannerqc;
    }

    public void setIdBannerqc(Integer idBannerqc) {
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

    public BannerQc(Integer idBannerqc, String name, String urlBanner, String createAt) {
        this.idBannerqc = idBannerqc;
        this.name = name;
        this.urlBanner = urlBanner;
        this.createAt = createAt;
    }
}