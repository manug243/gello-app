package de.gello.data.session

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.execute
import com.skash.forge.network.response.ApiResponse
import com.skash.forge.network.session.AuthTokens
import com.skash.forge.network.session.SessionExpirationHandler
import com.skash.forge.network.session.TokenAuthenticator
import de.gello.data.network.endpoint.Api
import de.gello.data.network.request.RefreshRequest
import de.gello.data.network.response.RefreshResponse
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
        val currentRefreshToken = sessionRepository
            .getRefreshToken()
            .getOrNull()
            .takeUnless { it.isNullOrBlank() } ?: return null

        val response = httpClient.execute<RefreshResponse, AuthTokens>(
            mapper = {
                AuthTokens(
                    bearer = it.access,
                    refresh = it.refresh
                )
            },
            requestBuilder = {
                post(Api.Auth.Refresh)
                body(RefreshRequest(refresh = currentRefreshToken))
            }
        )

        return when (response) {
            is ApiResponse.Error -> {
                sessionManagerHandler.onSessionExpired()
                null
            }

            is ApiResponse.Success -> {
                sessionRepository.setAuthToken(response.body.bearer)
                sessionRepository.setRefreshToken(response.body.refresh)
                response.body
            }
        }
    }

    override suspend fun clearToken() {
        sessionRepository.deleteRefreshToken()
        sessionRepository.deleteAuthToken()
    }
}