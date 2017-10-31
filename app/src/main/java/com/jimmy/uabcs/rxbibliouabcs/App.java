package com.jimmy.uabcs.rxbibliouabcs;

import android.app.Application;
import android.content.Context;

import com.jimmy.uabcs.rxbibliouabcs.api.ApiLibrary;
import com.jimmy.uabcs.rxbibliouabcs.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static final String BASE_URL = "http://aguacatero-001-site1.htempurl.com/api/";
    private static Context mContext;
    private static ApiLibrary api;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static ApiLibrary getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Constants.GSON))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            api = retrofit.create(ApiLibrary.class);
        }
        return api;
    }


    public static Context getContext() {
        return mContext;
    }
}
