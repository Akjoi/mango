package com.example.mango.authorization.entities

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String,

    @SerializedName("dial_code")
    val dial_code: String,

    @SerializedName("code")
    var code: String,
)
