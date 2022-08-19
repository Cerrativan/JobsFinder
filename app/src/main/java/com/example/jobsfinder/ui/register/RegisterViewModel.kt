package com.example.jobsfinder.ui.register

import androidx.lifecycle.*
import com.example.jobsfinder.ui.model.User
import com.example.jobsfinder.ui.service.UserRepository
import io.ktor.client.call.*
import kotlinx.coroutines.launch
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import java.util.*

class RegisterViewModel() : ViewModel() {

    private val repo = UserRepository()
    val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get () = _signUpResult


    fun signUp(username: String, email: String, password: String, type: String) {
        viewModelScope.launch {
                val user = User(UUID.randomUUID().toString(), username, email, password, type)
            runCatching {
                repo.signUp(user)
            }.onSuccess {
                if(it.body())
                    _signUpResult.postValue(true)
                else
                    _signUpResult.postValue(false)
            }.onFailure {
                _signUpResult.postValue(false)
            }
        }
    }

}