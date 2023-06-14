package com.example.mango.confirmcode.entities

import com.google.gson.annotations.SerializedName

data class ConfirmCodeResponse(
    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("access_token")
    val AccessToken: String,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("is_user_exists")
    val isUserExist: Boolean,
)

data class ConfirmCodeRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("code")
    val code: String,
)
