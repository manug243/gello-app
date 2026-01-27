package de.gello.app.feature.overview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.app.feature.overview.OverviewState
import de.gello.designsystem.component.Page
import de.gello.designsystem.theme.Spacing

@Composable
internal fun OverviewPage(
    state: OverviewState.Default
) {
    Page(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            Text("This is the overview screen, where all projects are listed")

            Text("Also, you can create projects here via the fab")
        }
    }
}