package com.example.jobsfinder

object ApiRoutes {
    const val BASE_URL_USERS = "http://192.168.1.97:8080/api/v1.0/users"
    const val BASE_URL_ADS = "http://192.168.1.97:8080/api/v1.0/ads"

    val signUp = "/add"
    val login = "/login"
    val publish = "/publish"
    val search = "/search"
    val searchAds = "/getMyAds"
    val deleteAd = "/deleteAd"
    val changePsw = "/changePsw"
    val searchByJob = "/searchByJob"
    val searchByCity = "/searchByCity"
    val searchAll = "/searchAll"
    val searchLatest = "/searchLatest"
}