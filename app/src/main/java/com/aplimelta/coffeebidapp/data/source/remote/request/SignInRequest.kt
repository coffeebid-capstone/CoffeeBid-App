package com.aplimelta.coffeebidapp.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
