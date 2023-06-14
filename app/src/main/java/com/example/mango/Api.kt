package com.example.mango

import com.example.mango.authorization.entities.CodeRequest
import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.authorization.entities.RefreshTokenRequest
import com.example.mango.authorization.entities.RefreshTokenResponse
import com.example.mango.confirmcode.entities.ConfirmCodeRequest
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import com.example.mango.registration.entities.RegistrationRequest
import com.example.mango.registration.entities.RegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun getCode(@Body codeRequest: CodeRequest): Response<CodeResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun confirmCode(@Body confirmCodeRequest: ConfirmCodeRequest): Response<ConfirmCodeResponse>

    @POST("/api/v1/users/register/")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("/api/v1/users/refresh-token/")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse>
}