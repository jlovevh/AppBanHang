package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TinCongNghe {

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
    @SerializedName("dmtt_id")
    @Expose
    private int dmtt_id;

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

    public int getDmtt_id() {
        return dmtt_id;
    }

    public void setDmtt_id(int dmtt_id) {
        this.dmtt_id = dmtt_id;
    }
}
