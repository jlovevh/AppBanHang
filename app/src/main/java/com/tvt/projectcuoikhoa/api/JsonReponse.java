package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.LaptopNew;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.PhoneHot;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.TinCongNghe;
import com.tvt.projectcuoikhoa.model.TinTuc;
import com.tvt.projectcuoikhoa.utils.Constant;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


//    @GET(value = "sanpham/dienthoai.php")
////    Call<List<Phone>> getALLPhone(@Query("page") int page);

    @GET(value = "sanpham/dienthoai.php")
    Call<List<Phone>> getALLPhone();

    @GET(value = "sanpham/laptop.php")
    Call<List<LapTop>> getALLLapTop();

    @GET(value = "sanpham/tablet.php")
    Call<List<Tablet>> getAllTablet();

    @Multipart
    @POST(value = "uploadhinhanh.php")
    Call<String> postImageUser(@Part MultipartBody multipartBody);

    @GET(value = "sanpham/dienthoaimoinhat.php")
    Call<List<PhoneHot>> getPhoneHot();

    @GET(value = "sanpham/laptopmoinhat.php")
    Call<List<LaptopNew>> getLaptopNew();

    @GET(value = "sanpham/tabletmoinhat.php")
    Call<List<Tablet>> getTabletNew();

    @GET(value = "tintuc/tincongnghe.php")
    Call<List<TinCongNghe>> getTinCongNghe();

}
