package de.gello.app.feature.overview.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.app.feature.overview.OverviewState
import de.gello.designsystem.component.JournalCard
import de.gello.designsystem.component.PageWithPaddingSlot
import de.gello.designsystem.theme.Spacing
import de.gello.util.helper.DateHelper

@Composable
internal fun OverviewPage(
    state: OverviewState.Default
) {
    PageWithPaddingSlot(
        modifier = Modifier
            .padding(horizontal = Spacing.Medium)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            state.journals.forEach { journal ->
                JournalCard(
                    colorIndicator = journal.color,
                    title = journal.title,
                    owner = journal.owner,
                    updatedAt = DateHelper.formatDateString(journal.updatedAt),
                    entryCount = journal.entries?.count() ?: 0,
                    onClick = {}
                )
            }

            AnimatedVisibility(
                visible = state.journals.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text("No journals created yet. Be the first to create a journal.")
            }
        }
    }
}