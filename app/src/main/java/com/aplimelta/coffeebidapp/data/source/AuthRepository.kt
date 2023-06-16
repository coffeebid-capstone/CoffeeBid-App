package com.aplimelta.coffeebidapp.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiConfigPredict
import com.aplimelta.coffeebidapp.data.source.remote.network.ApiServices
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.data.source.remote.response.ImageCoffeeResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.PredictResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.ProductResponseItem
import com.aplimelta.coffeebidapp.data.source.remote.response.ProfileResponse
import com.aplimelta.coffeebidapp.data.source.remote.response.SearchProductResponseItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import java.io.File

class AuthRepository private constructor(
    private val client: ApiServices,
) {
    fun signUp(signUpRequest: SignUpRequest): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = client.signup(signUpRequest)
            if (response != null) {
                emit(Result.Success(response.msg))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun signIn(signInRequest: SignInRequest): LiveData<Result<ProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = client.signIn(signInRequest)
            if (response != null) {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun profile(): LiveData<Result<ProfileResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val response = client.profile()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                    Log.d("Repository", response.code().toString())
                    Log.d("Repository", response.raw().headers["Cookie"].toString())
                    Log.d("Repository", response.headers()["Set-Cookie"].toString())
                }
            } else {
                emit(Result.Success(null))
                Log.d("Repository", response.code().toString())
                Log.d("Repository", response.raw().headers["Cookie"].toString())
                Log.d("Repository", response.headers()["Set-Cookie"].toString())
            }
        } catch (e: Exception) {
            emit(Result.Error(e.printStackTrace().toString()))
            Log.e("Repository", "profile: ${e.printStackTrace()}")
        }
    }

    fun signOut(): LiveData<String> = liveData {
        try {
            val response = client.signOut()
            emit(response.msg)
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    fun getAllProduct(): LiveData<Result<List<ProductResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = client.getAllProduct()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                } else {
                    emit(Result.Success(emptyList()))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
            Log.e("Repository", "getAllProduct: ${e.printStackTrace()}")
        }
    }

    fun searchProductByName(
        query: String,
    ): LiveData<Result<List<SearchProductResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = client.searchProductByName(query)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                } else {
                    emit(Result.Success(emptyList()))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
            Log.e("Repository", "getAllProduct: ${e.printStackTrace()}")
        }
    }

    fun image(image: File): LiveData<Result<ImageCoffeeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val imageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                image.name,
                imageFile
            )
            val response = client.image(multipart)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                }
            }
        } catch (e: IOException) {
            emit(Result.Error(e.toString()))
        }
    }

    fun predictQuality(image: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfigPredict.getApiServices().predictQuality(PredictResponse(image))
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

    fun predictRoast(image: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = ApiConfigPredict.getApiServices().predictRoast(PredictResponse(image))
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Result.Success(responseBody))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
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