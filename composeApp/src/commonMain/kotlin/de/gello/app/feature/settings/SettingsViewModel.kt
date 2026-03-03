package de.gello.app.feature.settings

import androidx.lifecycle.viewModelScope
import com.skash.forge.usecase.invoke
import de.gello.app.util.BaseViewModel
import de.gello.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<SettingsState, SettingsState.Intent>(
    initialState = SettingsState.Default()
) {
    override fun executeIntent(intent: SettingsState.Intent) {
        when (intent) {
            is SettingsState.Default.Intent.Logout -> handleIntent<_, _>(
                intent = intent,
                handler = ::handleLogoutButton
            )
        }
    }

    private fun handleLogoutButton(
        state: SettingsState,
        intent: SettingsState.Default.Intent.Logout
    ) {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}