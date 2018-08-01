package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.model.BannerQc;
import com.tvt.projectcuoikhoa.model.Cart;
import com.tvt.projectcuoikhoa.model.Comment;
import com.tvt.projectcuoikhoa.model.LapTop;
import com.tvt.projectcuoikhoa.model.News;
import com.tvt.projectcuoikhoa.model.Phone;
import com.tvt.projectcuoikhoa.model.Rating;
import com.tvt.projectcuoikhoa.model.Tablet;
import com.tvt.projectcuoikhoa.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST(value = "donhang.php")
    Call<String> postInformationCustomer(@Field("id_user") String id_user,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("phone") int phone,
                                         @Field("address") String address);

    @FormUrlEncoded
    @POST(value = "giohang.php")
    Call<String> postCartToService(@Field("json") String json);


    @FormUrlEncoded
    @POST(value = "chitiethoadon.php")
    Call<String> postChiTietDonHang(@Field("json") String json);

    @FormUrlEncoded
    @POST(value = "getHistorygiohang.php")
    Call<List<Cart>> getCartByUserId(@Field("id_user") String user_id);


    @FormUrlEncoded
    @POST(value = "updategiohang.php")
    Call<String> updateCartByIdSp(@Field("soluong") int soluong,
                                  @Field("giamoi") int giamoi,
                                  @Field("idSP") int idSp);

    @FormUrlEncoded
    @POST(value = "deletecartByIdUser.php")
    Call<String> deleteCartByIdUser(@Field("idUser") String idUser);


    @FormUrlEncoded
    @POST(value = "rating.php")
    Call<String> postRating(@Field("idsp") String idSp,
                            @Field("tenkhachhang") String tenkhachhang,
                            @Field("email") String email,
                            @Field("phone") String phone,
                            @Field("danhgia") int danhgia,
                            @Field("nhanxet") String nhanxet);

    @GET(value = "getratingdienthoai.php")
    Call<List<Rating>> getRatingPhone();

    @GET(value = "getratinglaptop.php")
    Call<List<Rating>> getRatingLaptop();

    @GET(value = "getratingtablet.php")
    Call<List<Rating>> getRatingTablet();


    @FormUrlEncoded
    @POST(value = "binhluan.php")
    Call<String> postComment(@Field("id_sp") String idSp,
                             @Field("tenKH") String tenkhachhang,
                             @Field("email") String email,
                             @Field("phone") String phone,
                             @Field("binhluan") String binhluan);

    @GET(value = "getBinhluanphone.php")
    Call<List<Comment>> getCommentPhone();

    @GET(value = "getBinhluanLaptop.php")
    Call<List<Comment>> getCommentLaptop();

    @GET(value = "getBinhluanTablet.php")
    Call<List<Comment>> getCommentTablet();

}
