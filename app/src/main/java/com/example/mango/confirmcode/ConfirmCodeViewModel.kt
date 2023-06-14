package com.example.mango.confirmcode

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.mango.appComponent
import com.example.mango.authorization.data.AuthorizationRepository
import com.example.mango.authorization.entities.Country
import com.example.mango.authorization.flagEmoji
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject

class ConfirmCodeViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var repo: AuthorizationRepository

    init {
        application.appComponent.inject(this)

    }

    fun confirmCode(code: String) {

    }

}