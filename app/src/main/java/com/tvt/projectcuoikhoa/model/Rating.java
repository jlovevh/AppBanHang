package com.tvt.projectcuoikhoa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idSP")
    @Expose
    private String idSP;
    @SerializedName("tenkhachhang")
    @Expose
    private String tenkhachhang;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("danhgia")
    @Expose
    private String danhgia;
    @SerializedName("nhanxet")
    @Expose
    private String nhanxet;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public Rating() {
    }

    public Rating(String id, String idSP, String tenkhachhang, String email, String phone, String danhgia, String nhanxet, String createAt) {
        this.id = id;
        this.idSP = idSP;
        this.tenkhachhang = tenkhachhang;
        this.email = email;
        this.phone = phone;
        this.danhgia = danhgia;
        this.nhanxet = nhanxet;
        this.createAt = createAt;
    }

    protected Rating(Parcel in) {
        id = in.readString();
        idSP = in.readString();
        tenkhachhang = in.readString();
        email = in.readString();
        phone = in.readString();
        danhgia = in.readString();
        nhanxet = in.readString();
        createAt = in.readString();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(String danhgia) {
        this.danhgia = danhgia;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idSP);
        dest.writeString(tenkhachhang);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(danhgia);
        dest.writeString(nhanxet);
        dest.writeString(createAt);
    }
}