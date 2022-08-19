package com.example.jobsfinder.utilities

import kotlinx.serialization.Serializable

@Serializable
data class ChangePassword(val email: String, val password: String) {
}