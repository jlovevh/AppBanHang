package com.tvt.projectcuoikhoa.api;

import com.tvt.projectcuoikhoa.utils.Const;

public class APIUtils {

    public static JsonReponse getJsonReponse(){
        return RetrofitClient.getClient(Const.BASE_URL).create(JsonReponse.class);
    }
}
