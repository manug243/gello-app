package de.gello.app.feature.overview.ui

import androidx.collection.emptyObjectList
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
import de.gello.designsystem.component.SearchField
import de.gello.designsystem.theme.Spacing
import de.gello.util.helper.DateHelper
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.overview_hint_no_journals
import gello.composeapp.generated.resources.overview_hint_no_matching_results
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OverviewPage(
    state: OverviewState.Default,
    onClick: (id: Int) -> Unit,
    onQueryChanged: (String) -> Unit
) {
    val emptyText: String? = when {
        state.allJournals.isEmpty() ->
            stringResource(Res.string.overview_hint_no_journals)

        state.query.isNotBlank() && state.journals.isEmpty() ->
            stringResource(Res.string.overview_hint_no_matching_results, state.query)

        else -> null
    }

    PageWithPaddingSlot(
        modifier = Modifier
            .padding(horizontal = Spacing.Medium)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {
            SearchField(
                query = state.query,
                onQueryChanged = onQueryChanged
            )

            state.journals.forEach { journal ->
                JournalCard(
                    colorIndicator = journal.color,
                    title = journal.title,
                    owner = "${journal.owner?.firstname} ${journal.owner?.lastname}",
                    updatedAt = DateHelper.formatDateString(journal.updatedAt),
                    entryCount = journal.entryCount ?: 0,
                    onClick = { onClick(journal.id) }
                )
            }

            AnimatedVisibility(
                visible = emptyText != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(emptyText.orEmpty())
            }
        }
    }
}