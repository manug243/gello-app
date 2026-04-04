package de.gello.app.feature.entryCreation

import androidx.lifecycle.viewModelScope
import com.skash.forge.navigation.NavigationEvent
import com.skash.forge.outcome.collectOutcome
import de.gello.app.util.BaseViewModel
import de.gello.domain.model.Entry
import de.gello.domain.model.GelImage
import de.gello.domain.model.Lane
import de.gello.domain.usecase.entry.CreateEntryUseCase
import de.gello.domain.usecase.image.UploadGelImageUseCase
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.io.encoding.Base64

class EntryCreationViewModel(
    private val journalId: Int,
    private val uploadGelImageUseCase: UploadGelImageUseCase,
    private val createEntryUseCase: CreateEntryUseCase
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
                it.copy(type = intent.value.name)
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

            is EntryCreationState.ThirdStep.Intent.SetLaneCount -> updateDraft {
                it.copy(laneCount = intent.value)
            }

            is EntryCreationState.ThirdStep.Intent.ToFourthStep -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleToFourthStepIntent
            )

            is EntryCreationState.FourthStep.Intent.ToThirdStep -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleBackToThirdStepIntent
            )

            is EntryCreationState.FourthStep.Intent.SaveEntry -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleSaveEntryIntent
            )

            is EntryCreationState.FourthStep.Intent.SetProbe -> updateDraft { draft ->
                draft.copy(
                    content = draft.content?.copy(
                        tableData = draft.content.tableData.map { lane ->
                            if (lane.lane == intent.lane) {
                                lane.copy(probe = intent.value)
                            } else {
                                lane
                            }
                        }
                    )
                )
            }

            is EntryCreationState.FourthStep.Intent.SetVolume -> updateDraft { draft ->
                draft.copy(
                    content = draft.content?.copy(
                        tableData = draft.content.tableData.map { lane ->
                            if (lane.lane == intent.lane) {
                                lane.copy(volume = intent.value.toIntOrNull())
                            } else {
                                lane
                            }
                        }
                    )
                )
            }

            is EntryCreationState.FourthStep.Intent.ShowDataTable -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleShowDataTableIntent
            )
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
                onSuccess = { response ->
                    val processedBytes = Base64.decode(response.processedImage)
                    setState(
                        EntryCreationState.ThirdStep(
                            draft = state.draft.copy(
                                content = response,
                                laneCount = response.laneCount.toString()
                            ),
                            optimizedImage = GelImage(
                                data = processedBytes,
                                name = "processed.png",
                                fileType = "png"
                            ),
                            showError = state.showError
                        )
                    )
                }
            )
        }
    }

    private fun handleToFourthStepIntent(
        state: EntryCreationState.ThirdStep,
        intent: EntryCreationState.ThirdStep.Intent.ToFourthStep
    ) {
        val newLaneCount = state.draft.laneCount?.toIntOrNull()
            ?: state.draft.content?.laneCount
            ?: 0

        val updatedTableData = buildLaneTableData(newLaneCount)

        val updatedContent = state.draft.content?.copy(
            laneCount = newLaneCount,
            tableData = updatedTableData
        )

        setState(
            EntryCreationState.FourthStep(
                draft = state.draft.copy(content = updatedContent),
                showError = state.showError
            )
        )
    }

    private fun handleBackToThirdStepIntent(
        state: EntryCreationState.FourthStep,
        intent: EntryCreationState.FourthStep.Intent.ToThirdStep
    ) {
        val processedBytes = state.draft.content?.processedImage
            ?.let { Base64.decode(it) }

        setState(
            EntryCreationState.ThirdStep(
                draft = state.draft,
                optimizedImage = processedBytes?.let {
                    GelImage(
                        data = it,
                        name = "processed.png",
                        fileType = "png"
                    )
                },
                showError = state.showError
            )
        )
    }

    private fun handleSaveEntryIntent(
        state: EntryCreationState.FourthStep,
        intent: EntryCreationState.FourthStep.Intent.SaveEntry
    ) {
        val content = state.draft.content ?: return

        val entry = Entry(
            journalId = journalId,
            name = state.draft.title,
            type = state.draft.type,
            content = Json.encodeToJsonElement(content)
        )

        viewModelScope.launch {
            createEntryUseCase(
                CreateEntryUseCase.Params(entry)
            ).collectOutcome(
                onProgress = {
                    setState(EntryCreationState.Loading)
                },
                onFailure = {
                    showSnackbar(it.message)
                },
                onSuccess = {
                    dispatchNavigationEvent(NavigationEvent.NavigateUp)
                }
            )
        }
    }

    private fun handleShowDataTableIntent(
        state: EntryCreationState.FourthStep,
        intent: EntryCreationState.FourthStep.Intent.ShowDataTable
    ) {
        val updatedTableData = state.draft.content?.tableData?.map { lane ->
            if (intent.value) {
                lane
            } else {
                lane.copy(
                    probe = "",
                    volume = null
                )
            }
        }

        setState(
            state.copy(
                showTable = intent.value,
                draft = state.draft.copy(
                    content = state.draft.content?.copy(
                        tableData = updatedTableData ?: emptyList()
                    )
                )
            )
        )
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
            is EntryCreationState.FourthStep -> s.draft
            else -> return
        }

        val newDraft = reducer(currentDraft)

        val newState = when (val s = currentState) {
            is EntryCreationState.FirstStep -> s.copy(draft = newDraft)
            is EntryCreationState.SecondStep -> s.copy(draft = newDraft)
            is EntryCreationState.ThirdStep -> s.copy(draft = newDraft)
            is EntryCreationState.FourthStep -> s.copy(draft = newDraft)
            else -> s
        }
        setState(newState)
    }
}

private fun buildLaneTableData(count: Int): List<Lane> {
    return (0 until count).map { index ->
        Lane(
            lane = ('A' + index).toString(),
            probe = "",
            volume = null
        )
    }
}