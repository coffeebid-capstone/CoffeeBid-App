package com.aplimelta.coffeebidapp.ui

import androidx.lifecycle.ViewModel
import com.aplimelta.coffeebidapp.data.source.AuthRepository
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import java.io.File

class MainViewModel(private val repository: AuthRepository) : ViewModel() {

    fun signUp(signUpRequest: SignUpRequest) = repository.signUp(signUpRequest)
    fun signIn(signInRequest: SignInRequest) = repository.signIn(signInRequest)
    val profile = repository.profile()
    val logout = repository.signOut()

    val coffees = repository.getAllProduct()
    fun searchProductByName(query: String) = repository.searchProductByName(query)
    fun image(image: File) = repository.image(image)

    fun predictQuality(image: String) = repository.predictQuality(image)
    fun predictRoast(image: String) = repository.predictRoast(image)

}