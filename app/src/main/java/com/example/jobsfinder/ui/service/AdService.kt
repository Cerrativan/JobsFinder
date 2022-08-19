package com.example.jobsfinder.ui.service

import android.util.Log
import com.example.jobsfinder.ApiRoutes
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.utilities.MyAds
import com.example.jobsfinder.utilities.SearchAd
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class AdService() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun publishAd(ad: Ad) : HttpResponse{
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.publish) {
            contentType(ContentType.Application.Json)
            setBody(ad)
        }
    }

    suspend fun searchAd(searchAd: SearchAd): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.search) {
            contentType(ContentType.Application.Json)
            setBody(searchAd)
        }
    }

    suspend fun getAds(myAds: MyAds): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.searchAds) {
            contentType(ContentType.Application.Json)
            setBody(myAds)
        }
    }

    suspend fun deleteAd(ad: Ad): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.deleteAd) {
            contentType(ContentType.Application.Json)
            setBody(ad)
        }
    }

    suspend fun searchByJob(searchAd: SearchAd): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.searchByJob) {
            contentType(ContentType.Application.Json)
            setBody(searchAd)
        }
    }

    suspend fun searchByCity(searchAd: SearchAd): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_ADS + ApiRoutes.searchByCity) {
            contentType(ContentType.Application.Json)
            setBody(searchAd)
        }
    }

    suspend fun searchAll(): HttpResponse {
        return client.get(ApiRoutes.BASE_URL_ADS + ApiRoutes.searchAll) {
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun searchLatest(): HttpResponse {
        return client.get(ApiRoutes.BASE_URL_ADS + ApiRoutes.searchLatest) {
            contentType(ContentType.Application.Json)
        }
    }
}