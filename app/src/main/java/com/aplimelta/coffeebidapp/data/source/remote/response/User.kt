package com.aplimelta.coffeebidapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("contact")
    val contact: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String,
)

