package com.synac.quiztime.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//providing http client / configuring http client
//this ktor client library is also compatible with the KMP
object HttpClientFactory {
    fun create(): HttpClient{ //return type = HttpClient
        return HttpClient(OkHttp){ //engine = OkHttp
            install(ContentNegotiation){ //ContentNegotiation = with this we define what type of data we are going to send or receive through this ktor client
                json(json = Json { ignoreUnknownKeys = true }) //sending and receiving json data
                //ignoreUnknownKeys = will ignore fields other then what we defined in our data class
            }
            install(HttpTimeout) { //our app will not wait indefinitely for a response from the server
                socketTimeoutMillis = 20_000L //20sec
                requestTimeoutMillis = 20_000L
            }
            install(Logging){
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}