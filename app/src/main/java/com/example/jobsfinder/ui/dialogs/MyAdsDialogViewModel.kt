package com.example.jobsfinder.ui.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.service.AdService
import io.ktor.http.*
import kotlinx.coroutines.launch

class MyAdsDialogViewModel : ViewModel() {
    val adService = AdService()

    val _myAdsResult = MutableLiveData<HttpStatusCode>()
    val myAdsResult: LiveData<HttpStatusCode> get () = _myAdsResult

    fun deleteAd(ad: Ad) {
        viewModelScope.launch {
           runCatching {
                adService.deleteAd(ad)
            }.onSuccess {
               if(it.status.equals(HttpStatusCode.OK))
                   _myAdsResult.postValue(HttpStatusCode.OK)
               else
                   _myAdsResult.postValue(HttpStatusCode.BadRequest)
           }.onFailure {
               _myAdsResult.postValue(HttpStatusCode.BadRequest)
           }
        }
    }
}