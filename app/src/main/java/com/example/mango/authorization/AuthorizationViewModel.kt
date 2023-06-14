package com.example.mango.authorization

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mango.appComponent
import com.example.mango.authorization.data.AuthorizationRepository
import com.example.mango.authorization.entities.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject

class AuthorizationViewModel(private val navController: NavController,application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var repo: AuthorizationRepository

    @Inject
    lateinit var gson: Gson

    private val locale = Locale("RU")


    private val _countries: MutableLiveData<List<Country>>  = MutableLiveData(emptyList())
    val countries: LiveData<List<Country>>  = _countries

    private val _phone: MutableLiveData<String>  = MutableLiveData("")
    val phone: LiveData<String>  = _phone

    private var unmaskedPhone = ""

    var localeIndex: Int = 0
    init {
        application.appComponent.inject(this)


        val typeToken = object : TypeToken<List<Country>>() {}.type
        val countries = gson.fromJson<List<Country>>(loadJSONFromAsset(), typeToken)
        countries.map { it.code = locale.flagEmoji(it.code) }
        _countries.value = countries

        localeIndex = _countries.value?.indexOfFirst{it.code == locale.flagEmoji("RU")}!!
        _phone.value = _countries.value!![localeIndex].dial_code

    }

    fun authorize(phone: String):Boolean {
        if (phone.length < 11) return false
        viewModelScope.launch {
            val result = repo.getCode(phone)
        }
        unmaskedPhone = phone
        return true
    }

    fun changePhone(position: Int) {
        _phone.value = _countries.value?.get(position)?.dial_code
    }

    fun confirmCode(code: String) {
        Log.i(TAG, unmaskedPhone)
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = getApplication<Application>().assets.open("countries.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "Cleared")
    }

}

fun Locale.flagEmoji(country: String): String {
        val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }