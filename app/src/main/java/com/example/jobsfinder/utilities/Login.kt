package com.example.jobsfinder.utilities

import kotlinx.serialization.Serializable

@Serializable
data class Login(val email: String, val password: String) {
}