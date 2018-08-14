package com.tvt.projectcuoikhoa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String idSP;
    private String name;
    private String image;
    private String price;
    private String dmSP;

    public Product(String idSP, String name, String image, String price, String dmSP) {
        this.idSP = idSP;
        this.name = name;
        this.image = image;
        this.price = price;
        this.dmSP = dmSP;
    }


    protected Product(Parcel in) {
        idSP = in.readString();
        name = in.readString();
        image = in.readString();
        price = in.readString();
        dmSP = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDmSP() {
        return dmSP;
    }

    public void setDmSP(String dmSP) {
        this.dmSP = dmSP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSP);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(price);
        dest.writeString(dmSP);
    }
}
