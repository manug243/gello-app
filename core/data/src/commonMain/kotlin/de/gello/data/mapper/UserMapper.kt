package de.gello.data.mapper

import de.gello.data.network.response.UserResponse
import de.gello.domain.model.User

internal fun UserResponse.toUser() = User(
    id = id,
    username = username
)