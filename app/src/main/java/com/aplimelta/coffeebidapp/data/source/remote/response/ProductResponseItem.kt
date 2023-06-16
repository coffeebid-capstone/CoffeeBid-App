package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponseItem(

    @field:SerializedName("endDate")
    val endDate: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("openPrice")
    val openPrice: Int,

    @field:SerializedName("finalPrice")
    val finalPrice: Int,

    @field:SerializedName("productPict")
    val productPict: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("winner")
    val winner: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("startDate")
    val startDate: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,
)
