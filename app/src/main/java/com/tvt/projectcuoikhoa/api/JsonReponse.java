package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.model.BannerQc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonReponse {

    @GET(value = "banner/all")
    Call<List<BannerQc>> getAllBanner();
}
