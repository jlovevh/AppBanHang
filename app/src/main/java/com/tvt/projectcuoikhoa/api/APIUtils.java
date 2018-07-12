package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.utils.Constant;

public class APIUtils {

    public static JsonReponse getJsonReponse(){
        return RetrofitClient.getClient(Constant.BASE_URL).create(JsonReponse.class);
    }
}
