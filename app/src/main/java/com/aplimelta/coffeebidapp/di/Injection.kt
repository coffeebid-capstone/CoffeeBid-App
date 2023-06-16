package com.aplimelta.coffeebidapp.di

import android.content.Context
import com.aplimelta.coffeebidapp.data.source.AuthRepository
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val client = ApiConfig.getApiServices(context)
        return AuthRepository.getInstance(client)
    }
}