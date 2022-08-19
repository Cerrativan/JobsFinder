package com.example.jobsfinder.ui.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class User(val id: String, val username: String, val email: String, val password: String, val type: String) {
}