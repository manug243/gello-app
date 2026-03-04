package de.gello.app.feature.journalDetail.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.gello.app.feature.journalDetail.JournalDetailState
import de.gello.designsystem.component.PageWithPaddingSlot
import de.gello.designsystem.theme.Spacing
import de.gello.util.enums.EntryEnum
import de.gello.util.helper.DateHelper
import de.gello.util.helper.parseHexColor
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.journal_detail_hint_no_entries
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun JournalDetailPage(
    state: JournalDetailState.Default
) {

    PageWithPaddingSlot(
        modifier = Modifier
            .padding(horizontal = Spacing.Medium)
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
        ) {

            state.journal.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            state.journal.entries.forEach { entry ->

                val entryIcon = EntryEnum.entries.firstOrNull { it.id == entry.id }
                    ?: EntryEnum.NOTE

                EntryCard(
                    title = entry.name,
                    owner = "",
                    updatedAt = DateHelper.formatDateString(entry.updatedAt),
                    indicatorColor = state.journal.color,
                    entryType = entryIcon,
                    onClick = {}
                )
            }

            AnimatedVisibility(
                visible = state.journal.entries.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(stringResource(Res.string.journal_detail_hint_no_entries))
            }
        }
    }
}

@Composable
fun EntryCard(
    title: String,
    owner: String,
    updatedAt: String,
    indicatorColor: String,
    entryType: EntryEnum,
    onClick: () -> Unit
) {
    val parsedColor = parseHexColor(indicatorColor)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.Medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.weight(1f))

            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = entryType.icon,
                contentDescription = null
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = parsedColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null
                )

                Spacer(Modifier.width(Spacing.ExtraSmall))

                Text(
                    text = owner,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )

                Spacer(Modifier.width(Spacing.ExtraSmall))

                Text(
                    text = updatedAt,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}