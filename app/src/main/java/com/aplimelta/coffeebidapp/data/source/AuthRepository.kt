package com.aplimelta.coffeebidapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiServices
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse

class AuthRepository private constructor(private val client: ApiServices) {
    fun signUp(signUpRequest: SignUpRequest): LiveData<String> = liveData {
        try {
            val response = client.signup(signUpRequest)
            emit(response.msg)
        } catch (e: Exception) {
            emit(e.toString())
        }
    }

    fun signIn(signInRequest: SignInRequest): LiveData<ProfileResponse> = liveData {
        try {
            val response = client.signIn(signInRequest)
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun profile(): LiveData<ProfileResponse> = liveData {
        try {
            val response = client.profile()
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun signOut(): LiveData<String> = liveData {
        try {
            val response = client.signOut()
            emit(response.msg)
        } catch (e: Exception) {
            emit(e.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(client: ApiServices) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(client)
            }.also { INSTANCE = it }
    }
}