package com.example.mango.authorization.data

import com.example.mango.Api
import com.example.mango.SecurePreferences
import com.example.mango.authorization.entities.CodeRequest
import com.example.mango.authorization.entities.CodeResponse
import com.example.mango.confirmcode.entities.ConfirmCodeRequest
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import com.example.mango.registration.entities.RegistrationRequest
import com.example.mango.registration.entities.RegistrationResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

const val AT = "ACCESS_TOKEN"
const val RT = "REFRESH_TOKEN"
const val TAG = "DEBUG_MANGO"
class AuthorizationRepository @Inject constructor(private val api: Api, private val prefs: SecurePreferences) {

    suspend fun getCode(phone: String): CodeResponse? {
        return api.getCode(CodeRequest(phone = phone)).body()
    }

    suspend fun confirmCode(phone: String, code: String): ConfirmCodeResponse? {
        val result = api.confirmCode(ConfirmCodeRequest(phone=phone, code = code)).body()
        if (result != null && result.isUserExist) {
 //           prefs.putString(AT, result.accessToken)
//          prefs.putString(RT, result.refreshToken)
        }
        return result
    }

    suspend fun registerUser(phone: String, name: String, userName: String): RegistrationResponse? {
        val result =
            api.registerUser(RegistrationRequest(phone=phone, name = name, userName = userName)).body()
        if (result != null) {
            prefs.putString(AT, result.accessToken)
            prefs.putString(RT, result.refreshToken)
        }
        return result
    }

}