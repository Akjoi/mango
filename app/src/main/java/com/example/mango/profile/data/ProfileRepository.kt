package com.example.mango.profile.data

import com.example.mango.Api
import com.example.mango.SecurePreferences
import com.example.mango.profile.entities.Avatar
import com.example.mango.profile.entities.ProfileResponse
import com.example.mango.profile.entities.User

import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: Api, private val prefs: SecurePreferences) {


    suspend fun getUser(): ProfileResponse? {
        return api.getUser().body()
    }


    suspend fun changeUser(user: User): Avatar? {
        return api.changeUser(user).body()
    }
}