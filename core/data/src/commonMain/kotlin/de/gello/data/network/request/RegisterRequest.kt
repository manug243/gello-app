package de.gello.data.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,

    @SerialName("first_name")
    val firstname: String,

    @SerialName("last_name")
    val lastname: String
)
