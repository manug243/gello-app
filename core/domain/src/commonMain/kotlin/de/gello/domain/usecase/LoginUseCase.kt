package de.gello.domain.usecase

import com.skash.forge.network.response.flatMap
import com.skash.forge.network.response.onSuccess
import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.model.User
import de.gello.domain.repository.SessionRepository
import de.gello.domain.repository.UserRepository
import kotlinx.coroutines.flow.FlowCollector

class LoginUseCase(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : FlowOutcomeUseCase<LoginUseCase.Params, User, ErrorType>() {

    data class Params(
        val username: String,
        val password: String
    )

    override suspend fun FlowCollector<Outcome<User, ErrorType>>.execute(
        params: Params
    ) {
        val result = userRepository
            .authenticateUser(params.username, params.password)
            .flatMap { token ->
                sessionRepository.setAuthToken(token.token)
                sessionRepository.setRefreshToken(token.refreshToken)
                userRepository.fetchSelfUser()
            }
            .onSuccess { user ->
                sessionRepository.setUserID(user.id)
            }

        emitFrom(result) { ErrorType.UnauthorizedAccess }
    }
}