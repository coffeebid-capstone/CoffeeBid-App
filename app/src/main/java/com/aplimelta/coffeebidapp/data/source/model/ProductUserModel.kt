package com.aplimelta.coffeebidapp.data.source.model

import com.google.gson.annotations.SerializedName

data class ProductUserModel(
    val createdAt: String,
    val password: String,
    val address: String,
    val role: String,
    val contact: String,
    val id: Int,
    val uuid: String,
    val email: String,
    val username: String,
    val updatedAt: String,
)
