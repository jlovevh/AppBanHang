package com.tvt.projectcuoikhoa.api;

import android.util.Log;

import com.tvt.projectcuoikhoa.model.Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAndUpdateCartToService {


    public static void postCartToService(Cart cart) {
        JSONArray jsonArray = new JSONArray();
        String json = null;


        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id_sp", cart.getId_sp());
            jsonObject.put("id_user", cart.getId_user());
            jsonObject.put("image", cart.getUrl_image());
            jsonObject.put("tensanpham", cart.getName());
            jsonObject.put("soluongsp", cart.getSoluong());
            jsonObject.put("tonggia1sanpham", cart.getPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        json = jsonArray.toString();
        Log.d("jsonnnnnnnnnnnnnn", json);

        APIUtils.getJsonReponse().postCartToService(json).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String MESS = response.body();
                Log.d("SEIASADAK", "post: " + MESS);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static void updateCart(int soluong, int price, int id_sp) {

        APIUtils.getJsonReponse().updateCartByIdSp(soluong, price, id_sp).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String me = response.body();
                Log.d("SEIASADAK", "update: " + me);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("SEIASADAK", "error" + t.getMessage());
            }
        });
    }
}
