package com.tvt.projectcuoikhoa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharepreferenceUtils {

    private static final String EMAIL = "email";
    private static final String PASS_WORD = "pass_word";

    private static SharepreferenceUtils sharepreferenceUtils = null;
    private static final String SHARE_NAME = "MobileShop";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharepreferenceUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }


    public static SharepreferenceUtils newInstance(Context context) {

        if (sharepreferenceUtils == null) {
            sharepreferenceUtils = new SharepreferenceUtils(context);
        }
        return sharepreferenceUtils;

    }

    public void saveEmail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return preferences.getString(EMAIL, "");
    }

    public void savePassWord(String passWord) {
        editor.putString(PASS_WORD, passWord);
        editor.commit();
    }

    public String getPassWord() {
        return preferences.getString(PASS_WORD, "");
    }

    public void getSharepreference(Context context) {
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);

    }
}
