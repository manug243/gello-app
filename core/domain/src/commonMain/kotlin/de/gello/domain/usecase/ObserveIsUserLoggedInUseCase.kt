package de.gello.domain.usecase

import com.skash.forge.usecase.FlowUseCase
import de.gello.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveIsUserLoggedInUseCase(
    private val sessionRepository: SessionRepository
) : FlowUseCase<Unit, Boolean>() {

    override fun execute(params: Unit): Flow<Boolean> =
        sessionRepository.observeAuthToken()
            .map { it.isNotBlank() }
}