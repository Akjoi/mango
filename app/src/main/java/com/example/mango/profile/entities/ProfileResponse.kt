package com.example.mango.profile.entities

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("profile_data")
    val profileData: User
)

data class User(

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("instagram")
    val instagram: String,

    @SerializedName("vk")
    val vk: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("avatar")
    val avatar: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("last")
    val last: String,

    @SerializedName("online")
    val online: Boolean,

    @SerializedName("created")
    val created: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("completed_task")
    val completedTask: Int,

    @SerializedName("avatars")
    val avatars: Avatar,
)

data class Avatar(
    @SerializedName("avatar")
    val avatar: Boolean,

    @SerializedName("bigAvatar")
    val bigAvatar: String,

    @SerializedName("miniAvatar")
    val miniAvatar: String,
)
