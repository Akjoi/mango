package com.example.mango.authorization.data

import com.example.mango.Api
import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthorizationRepository @Inject constructor(private val api: Api) {

    suspend fun getCode(phone: String): CodeResponse? {
        return api.getCode(phone).body()
    }

    suspend fun confirmCode(phone: String, code: String): ConfirmCodeResponse? {
        return api.confirmCode(phone, code).body()
    }
}