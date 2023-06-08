package com.aplimelta.coffeebidapp.ui

import androidx.lifecycle.ViewModel
import com.aplimelta.coffeebidapp.data.source.AuthRepository
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest

class MainViewModel(private val repository: AuthRepository) : ViewModel() {

    fun signUp(signUpRequest: SignUpRequest) = repository.signUp(signUpRequest)
    fun signIn(signInRequest: SignInRequest) = repository.signIp(signInRequest)
    val profile = repository.profile()
    val logout = repository.signOut()

}