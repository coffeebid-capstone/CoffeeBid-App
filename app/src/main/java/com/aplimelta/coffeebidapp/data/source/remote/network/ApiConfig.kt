package com.aplimelta.coffeebidapp.data.source.remote.network

import android.content.Context
import android.util.Log
import com.aplimelta.coffeebidapp.BuildConfig
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getApiServices(context: Context): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor { Log.d("OkHttp", it) }

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder().apply {
            followRedirects(true)
            followSslRedirects(true)
            retryOnConnectionFailure(true)
            addInterceptor(loggingInterceptor)
            cache(null)
            cookieJar(JavaNetCookieJar(CookieManager()))
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://coffeebid-capstone.et.r.appspot.com/")
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()

        return retrofit.create(ApiServices::class.java)
    }
}