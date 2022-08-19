package com.example.jobsfinder.ui.search

import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.service.AdService
import com.example.jobsfinder.utilities.AdWithOwner
import com.example.jobsfinder.utilities.SearchAd
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val adService = AdService()
    val _searchResult = MutableLiveData<ArrayList<AdWithOwner>>()
    val searchResult : LiveData<ArrayList<AdWithOwner>> get() = _searchResult

    val _searchFailed = MutableLiveData<Throwable>()
    val searchFailed: LiveData<Throwable> get() = _searchFailed

    fun search(job: String, city: String) {
        var list = ArrayList<AdWithOwner>()
        viewModelScope.launch {
            val searchAd = SearchAd(job, city)
            runCatching {
                adService.searchAd(searchAd)
            }.onSuccess {
                 list = it.body()
                _searchResult.postValue(list)
            }.onFailure {
               _searchFailed.postValue(it)
            }
        }
    }

    fun searchByJob(job: String) {
        var list = arrayListOf<AdWithOwner>()
        viewModelScope.launch {
            runCatching {
                adService.searchByJob(SearchAd(job, "null"))
            }.onSuccess {
                list = it.body()
                _searchResult.postValue(list)
            }.onFailure {
                _searchFailed.postValue(it)
            }
        }
    }

    fun searchByCity(city: String) {
        var list = arrayListOf<AdWithOwner>()
        viewModelScope.launch {
            runCatching {
                adService.searchByCity(SearchAd("null", city))
            }.onSuccess {
                list = it.body()
                _searchResult.postValue(list)
            }.onFailure {
                _searchFailed.postValue(it)
            }
        }
    }

    fun searchAll() {
        var list = arrayListOf<AdWithOwner>()
        viewModelScope.launch {
            runCatching {
                adService.searchAll()
            }.onSuccess {
                list = it.body()
                _searchResult.postValue(list)
            }.onFailure {
                _searchFailed.postValue(it)
            }
        }
    }

    fun searchValidation(job: EditText, city: EditText) {
        if(job.text.toString().isEmpty() && city.text.toString().isEmpty())
            searchAll()
        else if(job.text.toString().isEmpty() && !city.text.toString().isEmpty())
            searchByCity(city.text.toString())
        else if(city.text.toString().isEmpty() && !job.text.toString().isEmpty())
            searchByJob(job.text.toString())
        else
            search(job.text.toString(), city.text.toString())
    }
}