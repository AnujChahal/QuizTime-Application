package com.synac.quiztime.data.remote.dto
//dto = data transfer object
import kotlinx.serialization.Serializable

@Serializable //(from api we get json objects, but in app we use kotlin objects)
//kotlinx serialization library = for converting json objects to kotlin objects
data class QuizTopicDto ( //only job is to reflect the data we are getting through the network
    val id: String,
    val name: String,
    val imageUrl: String,
    val code: Int
)