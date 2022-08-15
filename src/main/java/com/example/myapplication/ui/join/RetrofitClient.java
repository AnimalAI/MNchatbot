package com.example.myapplication.ui.join;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "https://12b969e5-87a6-4fca-a942-178ea253fdbc.mock.pstmn.io";
    //옌 post 서버 : https://12b969e5-87a6-4fca-a942-178ea253fdbc.mock.pstmn.io
    //주현 서버 : https://7919ceb2-3999-4ed8-8bcc-16baaf4e62d4.mock.pstmn.io
    private static Retrofit retrofit = null;

    private RetrofitClient() { }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                    .build();
        }
        return retrofit;
    }
}
