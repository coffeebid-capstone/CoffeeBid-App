package com.aplimelta.coffeebidapp.data.source.remote.network

import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.AuthResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ImageCoffeeResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ProductResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.SearchProductResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("api/v1/product")
    suspend fun getAllProduct(): ProductResponse

    @GET("api/v1/product/search/{query}")
    suspend fun searchProductByName(
        @Path("query") query: String,
    ): SearchProductResponse

    @Multipart
    @POST("api/v1/image")
    suspend fun image(
        @Part image: MultipartBody.Part,
    ): ImageCoffeeResponse

}