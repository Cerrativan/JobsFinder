package com.example.jobsfinder.ui.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Ad(val id: String, val title: String, val city: String, val description: String, val salary: Int, val owner: String ) {
}