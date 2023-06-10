package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("ProductResponse")
	val productResponse: List<ProductResponseItem>
)