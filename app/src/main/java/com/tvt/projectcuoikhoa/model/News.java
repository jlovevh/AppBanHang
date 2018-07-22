package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tieude")
    @Expose
    private String tieude;
    @SerializedName("mota")
    @Expose
    private String mota;
    @SerializedName("baiviet")
    @Expose
    private String baiviet;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("anhtieude")
    @Expose
    private String anhtieude;
    @SerializedName("Id_DM")
    @Expose
    private String idDM;
    @SerializedName("tendanhmuctintuc")
    @Expose
    private String tendanhmuctintuc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getBaiviet() {
        return baiviet;
    }

    public void setBaiviet(String baiviet) {
        this.baiviet = baiviet;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getAnhtieude() {
        return anhtieude;
    }

    public void setAnhtieude(String anhtieude) {
        this.anhtieude = anhtieude;
    }

    public String getTendanhmuctintuc() {
        return tendanhmuctintuc;
    }

    public String getIdDM() {
        return idDM;
    }

    public void setIdDM(String idDM) {
        this.idDM = idDM;
    }

    public void setTendanhmuctintuc(String tendanhmuctintuc) {
        this.tendanhmuctintuc = tendanhmuctintuc;


    }

}