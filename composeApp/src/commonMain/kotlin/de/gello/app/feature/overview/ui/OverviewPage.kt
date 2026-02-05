package de.gello.app.feature.overview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.app.feature.overview.OverviewState
import de.gello.designsystem.component.JournalCard
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
            state.journals.forEach { journal ->
                JournalCard(
                    colorIndicator = journal.color,
                    title = journal.title,
                    owner = journal.owner,
                    updatedAt = journal.updatedAt,
                    onClick = {}
                )
            }
        }
    }
}