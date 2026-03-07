package de.gello.domain.usecase

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.model.User
import de.gello.domain.repository.UserRepository
import kotlinx.coroutines.flow.FlowCollector

class FetchUserUseCase(
    private val userRepository: UserRepository
) : FlowOutcomeUseCase<Unit, User, ErrorType>() {

    override suspend fun FlowCollector<Outcome<User, ErrorType>>.execute(
        params: Unit
    ) {
        emitFrom(userRepository.fetchSelfUser()) {
            ErrorType.Custom(reason ?: "Unknown Error")
        }
    }
}