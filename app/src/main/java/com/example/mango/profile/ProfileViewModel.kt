package com.example.mango.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mango.appComponent
import com.example.mango.authorization.data.AuthorizationRepository
import com.example.mango.profile.data.ProfileRepository
import com.example.mango.profile.entities.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var repo: ProfileRepository

    private val _user: MutableLiveData<User> = MutableLiveData(null)
    val user: LiveData<User> = _user

    init {
        application.appComponent.inject(this)
        viewModelScope.launch {
            _user.value =  repo.getUser()?.profileData
        }
    }
}