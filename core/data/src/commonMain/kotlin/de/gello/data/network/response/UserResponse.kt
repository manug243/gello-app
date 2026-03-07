package de.gello.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("first_name")
    val firstname: String,
    @SerialName("last_name")
    val lastname: String
)
