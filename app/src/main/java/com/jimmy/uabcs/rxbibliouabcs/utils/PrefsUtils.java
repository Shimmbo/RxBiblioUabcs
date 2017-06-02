package com.jimmy.uabcs.rxbibliouabcs.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jimmy.uabcs.rxbibliouabcs.App;
import com.jimmy.uabcs.rxbibliouabcs.models.LoginResponse;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.IS_USER_LOGIN;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.KEY_USER;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.PREFER_NAME;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.PRIVATE_MODE;

public class PrefsUtils {
    private SharedPreferences mPrefs;

    private Editor mEditor;


    public PrefsUtils() {
        mPrefs = App.getContext().getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        mEditor = mPrefs.edit();
    }

    public void saveUserSession(LoginResponse user) {
        mEditor.putBoolean(IS_USER_LOGIN, true);
        String json = GSON.toJson(user);
        mEditor.putString(KEY_USER, json);
        mEditor.commit();
    }

    public LoginResponse getUserDetails() {
        String json = mPrefs.getString(KEY_USER, null);
        return GSON.fromJson(json, LoginResponse.class);
    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }

    public boolean isUserLoggedIn() {
        return mPrefs.getBoolean(IS_USER_LOGIN, false);
    }
}
