package com.tvt.projectcuoikhoa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("evaluation")
    @Expose
    private String evaluation;
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
    @SerializedName("tendanhmuc")
    @Expose
    private String tendanhmuc;
    @SerializedName("url_banner")
    @Expose
    private String urlBanner;
    @SerializedName("manhinh")
    @Expose
    private String manhinh;
    @SerializedName("camera_truoc")
    @Expose
    private String cameraTruoc;
    @SerializedName("camera_sau")
    @Expose
    private String cameraSau;
    @SerializedName("ram")
    @Expose
    private String ram;
    @SerializedName("bonhotrong")
    @Expose
    private String bonhotrong;
    @SerializedName("cpu")
    @Expose
    private String cpu;
    @SerializedName("gpu")
    @Expose
    private String gpu;
    @SerializedName("dungluongpin")
    @Expose
    private String dungluongpin;
    @SerializedName("hedieuhanh")
    @Expose
    private String hedieuhanh;
    @SerializedName("chitietcauhinh")
    @Expose
    private String chitietcauhinh;


    public Phone() {
    }

    public Phone(String id, String name, String price, String status, String image, String evaluation, String promo1, String promo2, String promo3, String gioithieu, String tag, String createAt, String tendanhmuc, String urlBanner, String manhinh, String cameraTruoc, String cameraSau, String ram, String bonhotrong, String cpu, String gpu, String dungluongpin, String hedieuhanh, String chitietcauhinh) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.image = image;
        this.evaluation = evaluation;
        this.promo1 = promo1;
        this.promo2 = promo2;
        this.promo3 = promo3;
        this.gioithieu = gioithieu;
        this.tag = tag;
        this.createAt = createAt;
        this.tendanhmuc = tendanhmuc;
        this.urlBanner = urlBanner;
        this.manhinh = manhinh;
        this.cameraTruoc = cameraTruoc;
        this.cameraSau = cameraSau;
        this.ram = ram;
        this.bonhotrong = bonhotrong;
        this.cpu = cpu;
        this.gpu = gpu;
        this.dungluongpin = dungluongpin;
        this.hedieuhanh = hedieuhanh;
        this.chitietcauhinh = chitietcauhinh;
    }

    protected Phone(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        status = in.readString();
        image = in.readString();
        evaluation = in.readString();
        promo1 = in.readString();
        promo2 = in.readString();
        promo3 = in.readString();
        gioithieu = in.readString();
        tag = in.readString();
        createAt = in.readString();
        tendanhmuc = in.readString();
        urlBanner = in.readString();
        manhinh = in.readString();
        cameraTruoc = in.readString();
        cameraSau = in.readString();
        ram = in.readString();
        bonhotrong = in.readString();
        cpu = in.readString();
        gpu = in.readString();
        dungluongpin = in.readString();
        hedieuhanh = in.readString();
        chitietcauhinh = in.readString();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
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


    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public void setTendanhmuc(String tendanhmuc) {
        this.tendanhmuc = tendanhmuc;
    }

    public String getUrlBanner() {
        return urlBanner;
    }

    public void setUrlBanner(String urlBanner) {
        this.urlBanner = urlBanner;
    }

    public String getManhinh() {
        return manhinh;
    }

    public void setManhinh(String manhinh) {
        this.manhinh = manhinh;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
    }

    public String getCameraSau() {
        return cameraSau;
    }

    public void setCameraSau(String cameraSau) {
        this.cameraSau = cameraSau;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getBonhotrong() {
        return bonhotrong;
    }

    public void setBonhotrong(String bonhotrong) {
        this.bonhotrong = bonhotrong;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getDungluongpin() {
        return dungluongpin;
    }

    public void setDungluongpin(String dungluongpin) {
        this.dungluongpin = dungluongpin;
    }

    public String getHedieuhanh() {
        return hedieuhanh;
    }

    public void setHedieuhanh(String hedieuhanh) {
        this.hedieuhanh = hedieuhanh;
    }

    public String getChitietcauhinh() {
        return chitietcauhinh;
    }

    public void setChitietcauhinh(String chitietcauhinh) {
        this.chitietcauhinh = chitietcauhinh;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeString(image);
        dest.writeString(evaluation);
        dest.writeString(promo1);
        dest.writeString(promo2);
        dest.writeString(promo3);
        dest.writeString(gioithieu);
        dest.writeString(tag);
        dest.writeString(createAt);
        dest.writeString(tendanhmuc);
        dest.writeString(urlBanner);
        dest.writeString(manhinh);
        dest.writeString(cameraTruoc);
        dest.writeString(cameraSau);
        dest.writeString(ram);
        dest.writeString(bonhotrong);
        dest.writeString(cpu);
        dest.writeString(gpu);
        dest.writeString(dungluongpin);
        dest.writeString(hedieuhanh);
        dest.writeString(chitietcauhinh);
    }
}
