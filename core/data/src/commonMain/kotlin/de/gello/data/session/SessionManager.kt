package de.gello.data.session

import com.skash.forge.network.session.SessionExpirationHandler
import de.gello.domain.repository.SessionRepository

class SessionManager(
    private val sessionRepository: SessionRepository
) : SessionExpirationHandler {
    override suspend fun onSessionExpired() {
        sessionRepository.deleteAuthToken()
        sessionRepository.deleteRefreshToken()
        sessionRepository.deleteUserID()
    }
}