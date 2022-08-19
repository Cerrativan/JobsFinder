package com.example.jobsfinder.ui.service

import com.example.jobsfinder.ApiRoutes
import com.example.jobsfinder.ui.model.User
import com.example.jobsfinder.utilities.ChangePassword
import com.example.jobsfinder.utilities.Login
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom

class UserRepository() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun changePassword(changePsw: ChangePassword): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_USERS + ApiRoutes.changePsw) {
            contentType(ContentType.Application.Json)
            setBody(changePsw)
        }
    }

    suspend fun signUp(user: User): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_USERS + ApiRoutes.signUp) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun login(login: Login): HttpResponse {
        return client.post(ApiRoutes.BASE_URL_USERS + ApiRoutes.login) {
            contentType(ContentType.Application.Json)
            setBody(login)
        }
    }
}