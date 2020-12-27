package com.example.androidapplication.presentation;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class SharedPref {

    private SharedPreferences sharedPreferences;

    private static final String KEY_USER_NAME = "signed_user_name";
    private static final String KEY_LANGUAGE = "language";

    public void init(FragmentActivity fragmentActivity) {
        sharedPreferences = fragmentActivity.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    public void setLanguage(String language) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, "");

    }

    public void setUserName(String userName) {
        // TODO: 23.12.2020 apply() vs commit()
        sharedPreferences.edit().putString(KEY_USER_NAME, userName).apply();
    }
}
