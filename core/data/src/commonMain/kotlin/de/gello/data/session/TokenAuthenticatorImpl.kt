package de.gello.data.session

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.response.ApiResponse
import com.skash.forge.network.response.map
import com.skash.forge.network.response.onSuccess
import com.skash.forge.network.session.AuthTokens
import com.skash.forge.network.session.SessionExpirationHandler
import com.skash.forge.network.session.TokenAuthenticator
import de.gello.data.network.endpoint.Api
import de.gello.data.network.endpoint.HeaderValue
import de.gello.domain.repository.SessionRepository

class TokenAuthenticatorImpl(
    private val sessionRepository: SessionRepository,
    private val sessionManagerHandler: SessionExpirationHandler
) : TokenAuthenticator {
    override suspend fun loadTokens(httpClient: HttpClient): AuthTokens? {
        val access = sessionRepository
            .getAuthToken()
            .getOrNull()
            .takeUnless { it.isNullOrBlank() } ?: return null

        val refresh = sessionRepository
            .getRefreshToken()
            .getOrNull()
            .takeUnless { it.isNullOrBlank() } ?: return null

        return AuthTokens(
            bearer = access,
            refresh = refresh
        )
    }

    override suspend fun refreshTokens(httpClient: HttpClient): AuthTokens? {
        val refresh = sessionRepository
            .getRefreshToken()
            .getOrNull()
            .takeUnless { it.isNullOrBlank() } ?: return null

        val response = httpClient.executeRaw {
            get(Api.Auth.Refresh)
            header(HeaderValue.Bearer(refresh))
        }.map {
            // have to check if we need this
            it.headers["Authorization"]?.firstOrNull().orEmpty().replace("Bearer", "").trim()
        }.onSuccess { refresh ->
            sessionRepository.setAuthToken(refresh)
            sessionRepository.setRefreshToken(refresh)
        }

        when (response) {
            is ApiResponse.Error -> {
                sessionManagerHandler.onSessionExpired()
                return null
            }

            is ApiResponse.Success -> return AuthTokens(
                bearer = response.body,
                refresh = response.body
            )
        }
    }

    override suspend fun clearToken() {
        sessionRepository.deleteRefreshToken()
        sessionRepository.deleteAuthToken()
    }
}