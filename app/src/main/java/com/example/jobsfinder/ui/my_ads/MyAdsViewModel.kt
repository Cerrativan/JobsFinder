package com.example.jobsfinder.ui.my_ads

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.service.AdService
import com.example.jobsfinder.utilities.MyAds
import io.ktor.client.call.*
import kotlinx.coroutines.launch

class MyAdsViewModel : ViewModel() {

    val adService = AdService()
    val _myAdsResult = MutableLiveData<ArrayList<Ad>>()
    val myAdsResult : LiveData<ArrayList<Ad>> get() = _myAdsResult
    val _myAdsFailed = MutableLiveData<Throwable>()
    val myAdsFailed: LiveData<Throwable> get() = _myAdsFailed

    fun getAds(id: String) {
        var list = ArrayList<Ad>()
        viewModelScope.launch {
            runCatching {
                val myAds = MyAds(id)
                Log.i("owner", id)
                adService.getAds(myAds)
            }.onSuccess {
                list = it.body()
                _myAdsResult.postValue(list)
            }.onFailure {
                _myAdsFailed.postValue(it)
            }
        }
    }
}