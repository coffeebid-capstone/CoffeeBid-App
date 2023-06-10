package com.aplimelta.coffeebidapp.data.source.remote.network

import android.util.Log
import com.aplimelta.coffeebidapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiServices(): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor { Log.d("OkHttp", it) }

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

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