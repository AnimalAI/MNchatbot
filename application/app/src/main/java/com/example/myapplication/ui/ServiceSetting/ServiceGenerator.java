package com.example.myapplication.ui.ServiceSetting;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL = "http://43.200.87.239:8080";
    //aws 서버 : http://43.200.87.239:8080
    //옌 post 서버 : https://12b969e5-87a6-4fca-a942-178ea253fdbc.mock.pstmn.io
    //주현 서버 : https://7919ceb2-3999-4ed8-8bcc-16baaf4e62d4.mock.pstmn.io

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor("Bearer " + authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}