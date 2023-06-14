package com.example.mango.registration.entities

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("user_id")
    val userId: Int,
)


data class RegistrationRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val userName: String,
)