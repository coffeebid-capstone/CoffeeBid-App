package com.aplimelta.coffeebidapp.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiServices
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            Log.d("Repository", "signIn: $response")
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun profile(): LiveData<ProfileResponse?> {
        val profile = MutableLiveData<ProfileResponse?>()
        val response = client.profile()

        response.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>,
            ) {
                when {
                    response.isSuccessful -> {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            profile.value = responseBody
                        }
                    }

                    response.code() == 401 -> {
                        profile.value = null
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.d("Repository", t.toString())
                t.printStackTrace()
            }
        })

        return profile
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