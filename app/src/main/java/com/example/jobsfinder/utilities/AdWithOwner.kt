package com.example.jobsfinder.utilities

import kotlinx.serialization.Serializable

@Serializable
data class AdWithOwner(val id: String,
                       val title: String,
                       val city: String,
                       val description: String,
                       val salary: Int,
                       val owner: String) {
}