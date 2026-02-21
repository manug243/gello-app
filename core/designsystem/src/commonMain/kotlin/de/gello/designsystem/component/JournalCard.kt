package de.gello.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DataObject
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FilePresent
import androidx.compose.material.icons.outlined.Inbox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.gello.designsystem.theme.Spacing
import de.gello.util.helper.DateHelper
import de.gello.util.helper.parseHexColor

@Composable
fun JournalCard(
    colorIndicator: String,
    title: String,
    owner: String,
    updatedAt: String,
    entryCount: Int,
    onClick: () -> Unit
) {
    val color = parseHexColor(colorIndicator)
        ?.let { Color(it) }
        ?: Color.Blue

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
                .height(60.dp)
                .padding(horizontal = Spacing.Medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.weight(1f))

            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Outlined.Inbox,
                contentDescription = null
            )

            Text(
                text = entryCount.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = color
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
                    text = DateHelper.formatDateString(updatedAt),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}