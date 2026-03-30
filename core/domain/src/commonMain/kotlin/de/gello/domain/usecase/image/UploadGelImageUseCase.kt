package de.gello.domain.usecase.image

import com.skash.forge.outcome.Outcome
import com.skash.forge.usecase.FlowOutcomeUseCase
import de.gello.domain.error.ErrorType
import de.gello.domain.error.toErrorType
import de.gello.domain.model.GelEntry
import de.gello.domain.model.GelImage
import de.gello.domain.repository.GelImageRepository
import kotlinx.coroutines.flow.FlowCollector

class UploadGelImageUseCase(
    private val gelImageRepository: GelImageRepository
) : FlowOutcomeUseCase<UploadGelImageUseCase.Params, GelEntry, ErrorType>() {

    data class Params(
        val gelImage: GelImage
    )

    override suspend fun FlowCollector<Outcome<GelEntry, ErrorType>>.execute(
        params: Params
    ) {
        emitFrom(gelImageRepository.uploadImage(params.gelImage)) {
            toErrorType { ErrorType.Custom("Something went wrong.") }
        }
    }
}