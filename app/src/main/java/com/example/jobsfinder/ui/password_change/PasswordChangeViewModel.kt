package com.example.jobsfinder.ui.password_change

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.service.UserRepository
import com.example.jobsfinder.utilities.ChangePassword
import io.ktor.http.*
import kotlinx.coroutines.launch

class PasswordChangeViewModel : ViewModel() {

    val userRepository = UserRepository()
    val _changePswSuccess = MutableLiveData<HttpStatusCode>()
    val changePswSuccess: LiveData<HttpStatusCode> get() = _changePswSuccess
    val _changePswFailed = MutableLiveData<Throwable>()
    val changePswFailed: LiveData<Throwable> get() = _changePswFailed

    fun changePassword(email: String, password: String, confirm: String) {
        viewModelScope.launch {
            val changePsw = ChangePassword(email, password)
            runCatching {
                userRepository.changePassword(changePsw)
            }.onSuccess {
                if(it.status == HttpStatusCode.OK)
                    _changePswSuccess.postValue(HttpStatusCode.OK)
                else
                    _changePswSuccess.postValue(HttpStatusCode.BadRequest)
            }.onFailure {
                    _changePswFailed.postValue(it)
            }
        }
    }



    fun validation(password: EditText, confirmPassword: EditText): Boolean {
        if(password.text.toString().length < 8 || password.text.toString().length > 16 || password.text.toString().isEmpty()) {
            password.setError("Password not valid")
            return false
        }else if(!(confirmPassword.text.toString().equals(password.text.toString()))) {
            confirmPassword.setError("Passwords must match")
            return false
        }
        return true
    }
}