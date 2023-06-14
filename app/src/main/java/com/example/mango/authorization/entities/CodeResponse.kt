package com.example.mango.authorization.entities

import com.google.gson.annotations.SerializedName

data class CodeResponse(

    @SerializedName("is_success")
    val is_success: Boolean
)

data class CodeRequest (
    @SerializedName("phone")
    val phone: String
)
