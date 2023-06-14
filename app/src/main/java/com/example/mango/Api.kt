package com.example.mango

import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("send-auth-code/")
    suspend fun getCode(@Body phone: String): Response<CodeResponse>

    @POST("check-auth-code/")
    suspend fun confirmCode(@Body phone: String, @Body code: String): Response<ConfirmCodeResponse>
}