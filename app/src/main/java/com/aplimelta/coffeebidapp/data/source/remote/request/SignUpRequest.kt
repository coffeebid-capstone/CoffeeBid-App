package com.aplimelta.coffeebidapp.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("contact")
	val contact: String,

	@field:SerializedName("confirmPassword")
	val confirmPassword: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,
)
