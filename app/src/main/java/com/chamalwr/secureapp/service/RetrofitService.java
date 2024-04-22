package com.chamalwr.secureapp.service;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "BASE_URL/";
    private static final String PUBLIC_CERT_HASH = "sha256/<CERT_PIN>";
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .certificatePinner(
                            new CertificatePinner.Builder()
                                    .add(BASE_URL, PUBLIC_CERT_HASH)
                                    .build()
                    )
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
