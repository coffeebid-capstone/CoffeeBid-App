package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("image")
	val image: String
)
