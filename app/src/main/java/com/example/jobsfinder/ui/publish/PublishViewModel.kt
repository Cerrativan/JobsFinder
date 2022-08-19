package com.example.jobsfinder.ui.publish

import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.service.AdService
import io.ktor.http.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class PublishViewModel : ViewModel() {

    val adService = AdService()
    val _publishResult = MutableLiveData<HttpStatusCode>()
    val publishResult: LiveData<HttpStatusCode> get () = _publishResult

    fun publishAd(title: String, city: String, salary: String, description: String, owner: String) {
        viewModelScope.launch {
            val ad = Ad(UUID.randomUUID().toString(), title, city, description, Integer.parseInt(salary), owner)
            runCatching {
                adService.publishAd(ad)
            }.onSuccess {
                if(it.status.equals(HttpStatusCode.OK))
                    _publishResult.postValue(HttpStatusCode.OK)
                else
                    _publishResult.postValue(HttpStatusCode.BadRequest)
            }.onFailure {
                _publishResult.postValue(HttpStatusCode.BadRequest)
            }
        }
    }

    fun validation(title: EditText, city: EditText, salary: EditText, description: EditText): Boolean {

        if(title.text.toString().isEmpty()) {
            title.setError("Enter a valid title")
            return false
        }
        if(city.text.toString().isEmpty()) {
            city.setError("Enter a valid city")
            return false
        }
        if(salary.text.toString().isEmpty()) {
            salary.setError("Enter a valid salary")
            return false
        }
        if(description.text.toString().isEmpty()) {
            description.setError("Enter a valid description")
            return false
        }
        return true
    }
}