package com.aplimelta.coffeebidapp.data.source.remote.network

import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.AuthResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @POST("api/v1/signup")
    suspend fun signup(
        @Body signup: SignUpRequest,
    ): AuthResponse

    @POST("api/v1/auth/signIn")
    suspend fun signIn(
        @Body signIn: SignInRequest,
    ): ProfileResponse

    @GET("api/v1/profile")
    fun profile(): Call<ProfileResponse>

    @DELETE("api/v1/auth/signOut")
    suspend fun signOut(): AuthResponse


}