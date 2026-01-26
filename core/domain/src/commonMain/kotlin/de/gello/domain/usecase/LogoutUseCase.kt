package de.gello.domain.usecase

import com.skash.forge.network.client.StateClearable
import com.skash.forge.usecase.UseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.repository.SessionRepository

class LogoutUseCase(
    private val sessionRepository: SessionRepository,
    private val stateClearable: StateClearable
) : UseCase<Unit, Unit, ErrorType>() {

    override suspend fun UseCaseScope<ErrorType>.execute(
        params: Unit
    ) {
        stateClearable.clearState()
        sessionRepository.deleteUserID()
        sessionRepository.deleteAuthToken()
        sessionRepository.deleteRefreshToken()
    }

    override fun mapError(t: Throwable): ErrorType {
        return ErrorType.Custom("Unknown Error")
    }
}