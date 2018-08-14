package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SanPham {

    @SerializedName("id_sp")
    @Expose
    private String idSp;
    @SerializedName("ten_sp")
    @Expose
    private String tenSp;
    @SerializedName("gia_sp")
    @Expose
    private String giaSp;
    @SerializedName("tinhtrang_sp")
    @Expose
    private String tinhtrangSp;
    @SerializedName("anh_sp")
    @Expose
    private String anhSp;
    @SerializedName("anhkhuyenmai")
    @Expose
    private String anhkhuyenmai;
    @SerializedName("maanhnoibat")
    @Expose
    private String maanhnoibat;
    @SerializedName("danhgia")
    @Expose
    private String danhgia;
    @SerializedName("danhmuc_id")
    @Expose
    private String danhmucId;
    @SerializedName("id_bannerqc")
    @Expose
    private String idBannerqc;
    @SerializedName("promo1")
    @Expose
    private String promo1;
    @SerializedName("promo2")
    @Expose
    private String promo2;
    @SerializedName("promo3")
    @Expose
    private String promo3;
    @SerializedName("gioithieu")
    @Expose
    private String gioithieu;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(String giaSp) {
        this.giaSp = giaSp;
    }

    public String getTinhtrangSp() {
        return tinhtrangSp;
    }

    public void setTinhtrangSp(String tinhtrangSp) {
        this.tinhtrangSp = tinhtrangSp;
    }

    public String getAnhSp() {
        return anhSp;
    }

    public void setAnhSp(String anhSp) {
        this.anhSp = anhSp;
    }

    public String getAnhkhuyenmai() {
        return anhkhuyenmai;
    }

    public void setAnhkhuyenmai(String anhkhuyenmai) {
        this.anhkhuyenmai = anhkhuyenmai;
    }

    public String getMaanhnoibat() {
        return maanhnoibat;
    }

    public void setMaanhnoibat(String maanhnoibat) {
        this.maanhnoibat = maanhnoibat;
    }

    public String getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(String danhgia) {
        this.danhgia = danhgia;
    }

    public String getDanhmucId() {
        return danhmucId;
    }

    public void setDanhmucId(String danhmucId) {
        this.danhmucId = danhmucId;
    }

    public String getIdBannerqc() {
        return idBannerqc;
    }

    public void setIdBannerqc(String idBannerqc) {
        this.idBannerqc = idBannerqc;
    }

    public String getPromo1() {
        return promo1;
    }

    public void setPromo1(String promo1) {
        this.promo1 = promo1;
    }

    public String getPromo2() {
        return promo2;
    }

    public void setPromo2(String promo2) {
        this.promo2 = promo2;
    }

    public String getPromo3() {
        return promo3;
    }

    public void setPromo3(String promo3) {
        this.promo3 = promo3;
    }

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

}