package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.TinTuc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonReponse {

    @GET(value = "getbannerqc.php")
    Call<List<BannerQc>> getAllBanner();

    @GET(value = "tintuc/tinmoi.php")
    Call<List<TinTuc>> getTinMoi();

    @GET(value = "tintuc/meohay.php")
    Call<List<TinTuc>> getMeoHay();

    @GET(value = "tintuc/danhgia.php")
    Call<List<TinTuc>> getDanhGia();

    @GET(value = "tintuc/thitruong.php")
    Call<List<TinTuc>> getThiTruong();

    @GET(value = "tintuc/tingame.php")
    Call<List<TinTuc>> getTinGame();

    @GET(value = "tintuc/cuocsong.php")
    Call<List<TinTuc>> getTinTucCuocSong();


    @GET(value = "sanpham/dienthoai.php")
    Call<List<Phone>> getALLPhone();

    @GET(value = "sanpham/laptop.php")
    Call<List<LapTop>> getALLLapTop();

    @GET(value = "sanpham/tablet.php")
    Call<List<Tablet>> getAllTablet();

}
