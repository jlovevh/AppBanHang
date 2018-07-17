package com.tvt.projectcuoikhoa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tablet {

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
    @SerializedName("anhkhuyemmai")
    @Expose
    private String anhkhuyenmai;
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
    @SerializedName("cpu")
    @Expose
    private String cpu;
    @SerializedName("gpu")
    @Expose
    private String gpu;
    @SerializedName("ram")
    @Expose
    private String ram;
    @SerializedName("bonhotrong")
    @Expose
    private Object bonhotrong;
    @SerializedName("ketnoi")
    @Expose
    private String ketnoi;
    @SerializedName("chitietcauhinh")
    @Expose
    private String chitietcauhinh;


    public Tablet() {
    }

    public String getAnhkhuyenmai() {
        return anhkhuyenmai;
    }

    public void setAnhkhuyenmai(String anhkhuyenmai) {
        this.anhkhuyenmai = anhkhuyenmai;
    }

    public Tablet(String id, String name, String price, String status, String image, String evaluation, String promo1, String promo2, String promo3, String gioithieu, String tag, String createAt, String tendanhmuc, String urlBanner, String manhinh, String cameraTruoc, String cameraSau, String cpu, String gpu, String ram, Object bonhotrong, String ketnoi, String chitietcauhinh) {
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
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.bonhotrong = bonhotrong;
        this.ketnoi = ketnoi;
        this.chitietcauhinh = chitietcauhinh;
    }

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

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Object getBonhotrong() {
        return bonhotrong;
    }

    public void setBonhotrong(Object bonhotrong) {
        this.bonhotrong = bonhotrong;
    }

    public String getKetnoi() {
        return ketnoi;
    }

    public void setKetnoi(String ketnoi) {
        this.ketnoi = ketnoi;
    }

    public String getChitietcauhinh() {
        return chitietcauhinh;
    }

    public void setChitietcauhinh(String chitietcauhinh) {
        this.chitietcauhinh = chitietcauhinh;
    }

}