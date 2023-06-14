package com.example.mango

import com.example.mango.authorization.entities.CodeRequest
import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.confirmcode.entities.ConfirmCodeRequest
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun getCode(@Body codeRequest: CodeRequest): Response<CodeResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun confirmCode(@Body confirmCodeRequest: ConfirmCodeRequest): Response<ConfirmCodeResponse>
}