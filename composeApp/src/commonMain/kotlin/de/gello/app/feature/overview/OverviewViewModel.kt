package de.gello.app.feature.overview

import de.gello.app.event.UIEvent
import de.gello.app.util.BaseViewModel

class OverviewViewModel(
    // TODO adding fetch projects usecase
) : BaseViewModel<OverviewState, OverviewState.Intent>(
    initialState = OverviewState.Default(),
    useEventBus = false
) {
    override fun executeIntent(intent: OverviewState.Intent) {
        when (intent) {
            is OverviewState.Intent.NavigateToCreateProject -> {
                //needs to changed to a dispatch event later on
                sendUIEvent(UIEvent.Snackbar("Not implemented yet"))
            }
        }
    }
}