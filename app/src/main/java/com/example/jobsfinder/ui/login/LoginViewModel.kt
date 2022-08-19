package com.example.jobsfinder.ui.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.model.User
import com.example.jobsfinder.ui.service.UserRepository
import com.example.jobsfinder.utilities.Login
import io.ktor.client.call.*
import io.ktor.http.HttpStatusCode.Companion.OK
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    val userRepository = UserRepository()
    val _loginSuccess = MutableLiveData<User>()
    val loginSuccess: LiveData<User> get () = _loginSuccess
    val _loginFailed = MutableLiveData<Boolean>()
    val loginFailed: LiveData<Boolean> get () = _loginFailed


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val login = Login(email, password)
            runCatching {
                userRepository.login(login)
            }.onSuccess {
                if(it.status == OK) {
                    val user = it.body<User>()
                    _loginSuccess.postValue(user)
                }else
                    _loginFailed.postValue(false)
            }.onFailure {
                _loginFailed.postValue(false)
            }
        }
    }
}