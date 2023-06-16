package com.aplimelta.coffeebidapp.data.source.remote.network

import android.util.Log
import com.aplimelta.coffeebidapp.BuildConfig
import com.aplimelta.coffeebidapp.utils.StringConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiConfigPredict {
    fun getApiServices(): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor { Log.d("OkHttp", it) }

        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://flask-app-5afwsyiwwa-et.a.run.app/")
            addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))
            addConverterFactory(StringConverter())
            client(client)
        }.build()

        return retrofit.create(ApiServices::class.java)
    }
}