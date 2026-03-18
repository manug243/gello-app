package de.gello.app.feature.entryCreation

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.GelImage
import de.gello.domain.usecase.image.UploadGelImageUseCase
import kotlinx.coroutines.launch

class EntryCreationViewModel(
    private val uploadGelImageUseCase: UploadGelImageUseCase
) : BaseViewModel<EntryCreationState, EntryCreationState.Intent>(
    initialState = EntryCreationState.FirstStep(
        draft = EntryDraft(),
        showError = false
    ),
    useEventBus = false
) {

    override fun executeIntent(intent: EntryCreationState.Intent) {
        when (intent) {
            is EntryCreationState.Intent.NavigateUp ->
                dispatchNavigationEvent(NavigationEvent.NavigateUp)

            is EntryCreationState.FirstStep.Intent.SetEntryTitle -> updateDraft {
                it.copy(title = intent.value)
            }

            is EntryCreationState.FirstStep.Intent.SetEntryType -> updateDraft {
                it.copy(typeId = intent.value.id)
            }

            is EntryCreationState.FirstStep.Intent.ToSecondStep -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleToSecondStepIntent
            )

            is EntryCreationState.SecondStep.Intent.ToFirstStep -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleToFirstStepIntent
            )

            is EntryCreationState.SecondStep.Intent.SelectImage -> updateDraft {
                it.copy(gelImage = intent.value)
            }

            is EntryCreationState.SecondStep.Intent.ToThirdStepWithUpload -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleToThirdStepIntent
            )

            is EntryCreationState.SecondStep.Intent.CancelCrop -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleCropCancel
            )

            is EntryCreationState.ThirdStep.Intent.ToSecondStep -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleBackToSecondStep
            )

            is EntryCreationState.ThirdStep.Intent.ToFourthStep -> {

            }
        }
    }

    private fun handleToSecondStepIntent(
        state: EntryCreationState.FirstStep,
        intent: EntryCreationState.FirstStep.Intent.ToSecondStep
    ) {
        setState(
            EntryCreationState.SecondStep(
                draft = state.draft,
                showError = state.showError
            )
        )
    }

    private fun handleToFirstStepIntent(
        state: EntryCreationState.SecondStep,
        intent: EntryCreationState.SecondStep.Intent.ToFirstStep
    ) {
        setState(
            EntryCreationState.FirstStep(
                draft = state.draft,
                showError = state.showError
            )
        )
    }

    private fun handleToThirdStepIntent(
        state: EntryCreationState.SecondStep,
        intent: EntryCreationState.SecondStep.Intent.ToThirdStepWithUpload
    ) {
        viewModelScope.launch {
            state.draft.gelImage?.let {
                uploadGelImageUseCase(
                    UploadGelImageUseCase.Params(it)
                )
            }?.collectOutcome(
                onProgress = { setState(EntryCreationState.Loading) },
                onFailure = { showSnackbar(it.message) },
                onSuccess = { imageBytes ->
                    setState(
                        EntryCreationState.ThirdStep(
                            draft = state.draft,
                            optimizedImage = GelImage(
                                data = imageBytes,
                                name = "",
                                fileType = ""
                            ),
                            showError = state.showError
                        )
                    )
                }
            )
        }
    }

    private fun handleBackToSecondStep(
        state: EntryCreationState.ThirdStep,
        intent: EntryCreationState.ThirdStep.Intent.ToSecondStep
    ) {
        setState(
            EntryCreationState.SecondStep(
                draft = state.draft,
                showError = state.showError
            )
        )
    }

    private fun handleCropCancel(
        state: EntryCreationState.SecondStep,
        intent: EntryCreationState.SecondStep.Intent.CancelCrop
    ) {
        setState(
            EntryCreationState.SecondStep(
                draft = state.draft,
                showError = state.showError
            )
        )
    }

    private fun updateDraft(
        reducer: (EntryDraft) -> EntryDraft
    ) {
        val currentDraft = when (val s = currentState) {
            is EntryCreationState.FirstStep -> s.draft
            is EntryCreationState.SecondStep -> s.draft
            is EntryCreationState.ThirdStep -> s.draft
            else -> return
        }

        val newDraft = reducer(currentDraft)

        val newState = when (val s = currentState) {
            is EntryCreationState.FirstStep -> s.copy(draft = newDraft)
            is EntryCreationState.SecondStep -> s.copy(draft = newDraft)
            is EntryCreationState.ThirdStep -> s.copy(draft = newDraft)
            else -> s
        }
        setState(newState)
    }
}