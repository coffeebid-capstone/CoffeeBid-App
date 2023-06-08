package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("msg")
	val msg: String
)
