package com.aplimelta.coffeebidapp.di

import android.content.Context
import com.aplimelta.coffeebidapp.data.source.AuthRepository
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiConfig

object Injection {
    fun provideRepository(): AuthRepository {
        val client = ApiConfig.getApiServices()
        return AuthRepository.getInstance(client)
    }
}