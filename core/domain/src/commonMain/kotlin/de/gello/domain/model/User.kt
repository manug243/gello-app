package de.gello.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val firstname: String,
    val lastname: String
) {
    companion object {
        val emptyUser = User(
            id = -1,
            username = "",
            firstname = "",
            lastname = ""
        )
    }
}
