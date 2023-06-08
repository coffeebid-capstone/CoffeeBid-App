package com.aplimelta.coffeebidapp.data.source.remote.network

import com.aplimelta.coffeebidapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiServices(): ApiServices {
        val loggingInterceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://coffeebid-capstone.et.r.appspot.com/")
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()

        return retrofit.create(ApiServices::class.java)
    }
}