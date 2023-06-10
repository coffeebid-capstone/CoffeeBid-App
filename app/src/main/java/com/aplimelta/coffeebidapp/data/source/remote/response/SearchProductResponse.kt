package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchProductResponse(

	@field:SerializedName("SearchProductResponse")
	val searchProductResponse: List<SearchProductResponseItem>
)