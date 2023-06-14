package com.example.mango.authorization

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.mango.R
import com.example.mango.appComponent
import com.example.mango.authorization.data.AuthorizationRepository
import com.example.mango.authorization.entities.Country
import com.example.mango.confirmcode.entities.ConfirmCodeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.inject.Inject

class AuthorizationViewModel(private val navController: NavController, context: Context) :
    ViewModel() {

    @Inject
    lateinit var repo: AuthorizationRepository

    @Inject
    lateinit var gson: Gson

    private val locale = Locale("RU")


    private val _countries: MutableLiveData<List<Country>> = MutableLiveData(emptyList())
    val countries: LiveData<List<Country>> = _countries

    private val _phone: MutableLiveData<String> = MutableLiveData("")
    val phone: LiveData<String> = _phone

    var unmaskedPhone = ""
        private set

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _localeIndex: MutableLiveData<Int> = MutableLiveData(0)
    val localeIndex: LiveData<Int> = _localeIndex

    init {
        context.appComponent.inject(this)
        Log.i("ViewModel Init", this.hashCode().toString())

        val typeToken = object : TypeToken<List<Country>>() {}.type
        val countries = gson.fromJson<List<Country>>(loadJSONFromAsset(context), typeToken)
        countries.map { it.code = locale.flagEmoji(it.code) }
        _countries.value = countries

        _localeIndex.value = _countries.value?.indexOfFirst { it.code == locale.flagEmoji("RU") }
        _phone.value = _countries.value!![_localeIndex.value!!].dial_code

    }

    fun authorize(phone: String) {

        viewModelScope.launch {
            _loading.value = true
            val result = repo.getCode("+$phone") ?: return@launch
            _loading.value = false

            if (!result.is_success) return@launch
            unmaskedPhone = phone
            navController.navigate(R.id.auth_to_code)
        }
    }

    fun changePhone(position: Int) {
        _phone.value = _countries.value?.get(position)?.dial_code
    }

    fun confirmCode(code: String) {

        viewModelScope.launch {
            _loading.value = true
            val result = repo.confirmCode("+$unmaskedPhone", code) ?: return@launch
            _loading.value = false
            result.let {
                if (!it.isUserExist) navController.navigate(R.id.code_to_register)
                else {
                    navController.navigate(R.id.code_to_profile)
                    onCleared()
                }
            }
        }



    }

    fun registerUser(name: String, userName: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = repo.registerUser("+$unmaskedPhone", name, userName) ?: return@launch
            navController.navigate(R.id.register_to_profile)
            _loading.value = false
        }
    }
    fun onTextChange(phone: String) {
        val index = _countries.value!!.indexOfFirst { it.dial_code == "+$phone" }
        if (index != -1) {
            _localeIndex.value = index
        }
    }

    private fun loadJSONFromAsset(ctx: Context): String? {
        val json: String? = try {
            val `is`: InputStream = ctx.assets.open("countries.json")
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

    class Factory(private val navController: NavController, private val context: Context) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthorizationViewModel(navController = navController, context = context) as T
        }
    }

}

fun Locale.flagEmoji(country: String): String {
    val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}