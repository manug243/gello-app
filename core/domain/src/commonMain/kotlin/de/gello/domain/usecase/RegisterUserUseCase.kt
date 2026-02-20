package de.gello.domain.usecase

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.repository.UserRepository
import kotlinx.coroutines.flow.FlowCollector

class RegisterUserUseCase(
    private val userRepository: UserRepository
) : FlowOutcomeUseCase<RegisterUserUseCase.Params, Unit, ErrorType>() {

    data class Params(
        val username: String,
        val password: String
    )

    override suspend fun FlowCollector<Outcome<Unit, ErrorType>>.execute(
        params: Params
    ) {
        val result = userRepository.registerUser(
            username = params.username,
            password = params.password
        )

        emitFrom(result) {
            toErrorType { ErrorType.RegistrationFailed }
        }
    }
}