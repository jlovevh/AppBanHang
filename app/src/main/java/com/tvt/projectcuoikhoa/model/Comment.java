package com.tvt.projectcuoikhoa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {

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
    @SerializedName("binhluan")
    @Expose
    private String binhluan;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    protected Comment(Parcel in) {
        id = in.readString();
        idSP = in.readString();
        tenkhachhang = in.readString();
        email = in.readString();
        phone = in.readString();
        binhluan = in.readString();
        createAt = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
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

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
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
        dest.writeString(binhluan);
        dest.writeString(createAt);
    }
}