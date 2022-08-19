package com.example.jobsfinder.ui.home

import android.content.Context
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.service.AdService
import com.example.jobsfinder.utilities.AdWithOwner
import com.example.jobsfinder.utilities.SearchAd
import io.ktor.client.call.*
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    val adService = AdService()
    val _homeResult = MutableLiveData<ArrayList<AdWithOwner>>()
    val homeResult : LiveData<ArrayList<AdWithOwner>> get() = _homeResult

    val _homeFailed = MutableLiveData<Throwable>()
    val homeFailed: LiveData<Throwable> get() = _homeFailed

    fun searchLatest() {
        var list = ArrayList<AdWithOwner>()
        viewModelScope.launch {
            runCatching {
                adService.searchLatest()
            }.onSuccess {
                list = it.body()
                _homeResult.postValue(list)
            }.onFailure {
                _homeFailed.postValue(it)
            }
        }
    }
}