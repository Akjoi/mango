package com.example.mango.authorization.data

import com.example.mango.Api
import com.example.mango.authorization.entities.CodeRequest
import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.confirmcode.entities.ConfirmCodeRequest
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthorizationRepository @Inject constructor(private val api: Api) {

    suspend fun getCode(phone: String): CodeResponse? {
        return api.getCode(CodeRequest(phone = phone)).body()
    }

    suspend fun confirmCode(phone: String, code: String): ConfirmCodeResponse? {
        val a = api.confirmCode(ConfirmCodeRequest(phone=phone, code = code)).body()
        return a
    }
}