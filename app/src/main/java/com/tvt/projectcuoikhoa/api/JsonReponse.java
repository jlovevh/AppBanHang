package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.News;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface JsonReponse {
    @GET(value = "getbannerqc.php")
    Call<List<BannerQc>> getAllBanner();

    @GET(value = "tintuc/tinmoi.php")
    Call<List<News>> getTinMoi();

    @GET(value = "tintuc/meohay.php")
    Call<List<News>> getMeoHay();

    @GET(value = "tintuc/danhgia.php")
    Call<List<News>> getDanhGia();

    @GET(value = "tintuc/thitruong.php")
    Call<List<News>> getThiTruong();

    @GET(value = "tintuc/tingame.php")
    Call<List<News>> getTinGame();

    @GET(value = "tintuc/cuocsong.php")
    Call<List<News>> getTinTucCuocSong();


//    @POST(value = "sanpham/dienthoai.php")
//    Call<List<Phone>> getALLPhone(@Query("page") int page);

    @GET(value = "sanpham/dienthoai.php")
    Call<List<Phone>> getALLPhone();

    @GET(value = "sanpham/laptop.php")
    Call<List<LapTop>> getALLLapTop();

    @GET(value = "sanpham/tablet.php")
    Call<List<Tablet>> getAllTablet();

    @Multipart
    @POST(value = "uploadhinhanh.php")
    Call<String> postImageUser(@Part MultipartBody.Part multipartBody);

    @GET(value = "sanpham/dienthoaimoinhat.php")
    Call<List<Phone>> getPhoneHot();

    @GET(value = "sanpham/laptopmoinhat.php")
    Call<List<LapTop>> getLaptopNew();

    @GET(value = "sanpham/tabletmoinhat.php")
    Call<List<Tablet>> getTabletNew();

    @GET(value = "tintuc/tincongnghe.php")
    Call<List<News>> getTinCongNghe();

    @FormUrlEncoded
    @POST(value = "signup.php")
    Call<String> signUp(@Field("username") String username,
                        @Field("password") String password,
                        @Field("name") String name,
                        @Field("email") String email,
                        @Field("phone") int phone,
                        @Field("address") String address,
                        @Field("image") String image);

    @FormUrlEncoded
    @POST(value = "login.php")
    Call<List<User>> getAllUser(@Field("email") String email,
                                @Field("password") String pass);

}
