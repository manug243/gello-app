@file:OptIn(ExperimentalMaterial3Api::class)

package de.gello.app.feature.entryCreation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.feature.entryCreation.EntryCreationState.ThirdStep.Intent
import de.gello.app.feature.entryCreation.ui.EntryCreationFirstStepPage
import de.gello.app.feature.entryCreation.ui.EntryCreationFourthStepPage
import de.gello.app.feature.entryCreation.ui.EntryCreationSecondStepPage
import de.gello.app.feature.entryCreation.ui.EntryCreationThirdStepPage
import de.gello.designsystem.component.CoveringProgressIndicator
import de.gello.designsystem.component.ScreenScaffold
import de.gello.designsystem.component.TopAppBarWithBackNavigation
import kotlinx.coroutines.flow.Flow

@Composable
fun EntryCreationScreen(viewmodel: EntryCreationViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()

    EntryCreationScreenImpl(
        state = state,
        executeIntent = viewmodel::executeIntent,
        events = viewmodel.events
    )
}

@Composable
fun EntryCreationScreenImpl(
    state: EntryCreationState,
    executeIntent: (EntryCreationState.Intent) -> Unit,
    events: Flow<UIEvent>
) {
    UIEventHandler(
        uiEvents = events
    ) { snackBarHost ->
        when (state) {
            is EntryCreationState.Loading -> CoveringProgressIndicator()
            is EntryCreationState.FirstStep -> FirstPage(
                snackBarHost = snackBarHost,
                state = state,
                executeIntent = executeIntent
            )

            is EntryCreationState.SecondStep -> SecondPage(
                snackBarHost = snackBarHost,
                state = state,
                executeIntent = executeIntent
            )

            is EntryCreationState.ThirdStep -> ThirdPage(
                snackBarHost = snackBarHost,
                state = state,
                executeIntent = executeIntent
            )

            is EntryCreationState.FourthStep -> FourthPage(
                snackBarHost = snackBarHost,
                state = state,
                executeIntent = executeIntent
            )
        }
    }
}

@Composable
private fun FirstPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryCreationState.FirstStep,
    executeIntent: (EntryCreationState.Intent) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBar(
                title = {
                    Text("Create a new entry")
                }
            )
        }
    ) {
        EntryCreationFirstStepPage(
            state = state,
            onTitleChanged = {
                executeIntent(EntryCreationState.FirstStep.Intent.SetEntryTitle(it))
            },
            onNextStepClick = {
                executeIntent(EntryCreationState.FirstStep.Intent.ToSecondStep)
            },
            onCancelClick = {
                executeIntent(EntryCreationState.Intent.NavigateUp)
            },
            onSelectedItem = {
                executeIntent(EntryCreationState.FirstStep.Intent.SetEntryType(it))
            }
        )
    }
}

@Composable
private fun SecondPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryCreationState.SecondStep,
    executeIntent: (EntryCreationState.Intent) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = state.draft.title,
                onNavigateBack = {
                    executeIntent(EntryCreationState.SecondStep.Intent.ToFirstStep)
                }
            )
        }
    ) {
        EntryCreationSecondStepPage(
            state = state,
            onCancelClick = {
                executeIntent(EntryCreationState.Intent.NavigateUp)
            },
            onCropCancelClick = {
                executeIntent(EntryCreationState.SecondStep.Intent.CancelCrop)
            },
            onNextStepClick = {
                executeIntent(EntryCreationState.SecondStep.Intent.ToThirdStepWithUpload(it))
            },
            selectedImage = {
                executeIntent(EntryCreationState.SecondStep.Intent.SelectImage(it))
            }
        )
    }
}

@Composable
private fun ThirdPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryCreationState.ThirdStep,
    executeIntent: (EntryCreationState.Intent) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = "Edit your image",
                onNavigateBack = {
                    executeIntent(Intent.ToSecondStep)
                }
            )
        }
    ) {
        EntryCreationThirdStepPage(
            state = state,
            onLaneCountChange = {
                executeIntent(Intent.SetLaneCount(it))
            },
            onCancelClick = {
                executeIntent(EntryCreationState.Intent.NavigateUp)
            },
            onNextStepClick = {
                executeIntent(Intent.ToFourthStep)
            }
        )
    }
}

@Composable
private fun FourthPage(
    snackBarHost: @Composable () -> Unit = {},
    state: EntryCreationState.FourthStep,
    executeIntent: (EntryCreationState.Intent) -> Unit
) {
    ScreenScaffold(
        snackbarHost = snackBarHost,
        topBar = {
            TopAppBarWithBackNavigation(
                title = "Edit your gel entry",
                onNavigateBack = {
                    executeIntent(EntryCreationState.FourthStep.Intent.ToThirdStep)
                }
            )
        }
    ) {
        EntryCreationFourthStepPage(
            state = state,
            onCancelClick = {
                executeIntent(EntryCreationState.Intent.NavigateUp)
            },
            onSaveClick = {
                executeIntent(EntryCreationState.FourthStep.Intent.SaveEntry)
            },
            onProbeChange = { lane, value ->
                executeIntent(EntryCreationState.FourthStep.Intent.SetProbe(lane, value))

            },
            onVolumeChange = { lane, value ->
                executeIntent(EntryCreationState.FourthStep.Intent.SetVolume(lane, value))
            },
            onShowTableChange = {
                executeIntent(EntryCreationState.FourthStep.Intent.ShowDataTable(it))
            }
        )
    }
}