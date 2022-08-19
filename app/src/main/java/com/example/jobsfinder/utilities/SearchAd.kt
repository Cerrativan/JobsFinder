package com.example.jobsfinder.utilities

import kotlinx.serialization.Serializable

@Serializable
class SearchAd(val job: String, val city: String) {
}