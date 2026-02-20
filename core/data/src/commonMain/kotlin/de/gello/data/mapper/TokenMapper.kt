package de.gello.data.mapper

import de.gello.data.network.response.TokenResponse
import de.gello.domain.model.Token

internal fun TokenResponse.toToken() = Token(
    token = accessToken,
    refreshToken = refreshToken
)