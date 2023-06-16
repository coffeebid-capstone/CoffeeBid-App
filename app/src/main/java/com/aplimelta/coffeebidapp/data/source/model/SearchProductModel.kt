package com.aplimelta.coffeebidapp.data.source.model

import com.google.gson.annotations.SerializedName

data class SearchProductModel(
    val endDate: String,
    val description: String,
    val openPrice: Int,
    val finalPrice: Int,
    val productPict: String,
    val type: String,
    val uuid: String,
    val userId: Int,
    val createdAt: String,
    val winner: String,
    val name: String,
    val id: Int,
    val startDate: String,
    val status: String,
    val updatedAt: String
)
