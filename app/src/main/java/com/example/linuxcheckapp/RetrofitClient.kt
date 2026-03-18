package com.example.linuxcheckapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    //伺服器位址
    private const val BASE_URL = "http://192.168.1.100:8000/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // 處理純文字
            .addConverterFactory(GsonConverterFactory.create())    // 處理 JSON
            .build()
            .create(ApiService::class.java)
    }
}