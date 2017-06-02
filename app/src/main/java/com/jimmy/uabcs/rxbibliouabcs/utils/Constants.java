package com.jimmy.uabcs.rxbibliouabcs.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface Constants {
    String EMPTY_STRING = "";
    String PREFER_NAME = "Prefs";
    String IS_USER_LOGIN = "IsUserLoggedIn";
    String KEY_USER = "User";
    int PRIVATE_MODE = 0;
    String URL = "http://shimmbo-001-site1.ctempurl.com/";
    String DEFAULT_IMAGE = "images/default.jpg";
    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    Gson GSON = new GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create();
}
