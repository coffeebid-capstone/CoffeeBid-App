package com.aplimelta.coffeebidapp.data.source.remote.network

import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.AuthResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @POST("api/v1/signup")
    suspend fun signup(
        @Body signup: SignUpRequest,
    ): AuthResponse

    @POST("api/v1/signIn")
    suspend fun signIn(
        @Body signIn: SignInRequest,
    ): ProfileResponse

    @GET("api/v1/signIn")
    suspend fun profile(): ProfileResponse

    @GET("api/v1/signOut")
    suspend fun signOut(): AuthResponse


}