package de.gello.app.feature.overview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.gello.app.feature.overview.OverviewState
import de.gello.designsystem.component.Page
import de.gello.designsystem.component.ProjectCard
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

            // just for testing
            ProjectCard(
                colorIndicator = Color.Cyan,
                title = "Project A",
                date = "27/01/2026",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Green,
                title = "Project V",
                date = "19/04/2024",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Magenta,
                title = "Project X",
                date = "14/01/2026",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Yellow,
                title = "Project T",
                date = "12/12/2025",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Cyan,
                title = "Project A",
                date = "27/01/2026",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Green,
                title = "Project V",
                date = "19/04/2024",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Magenta,
                title = "Project X",
                date = "14/01/2026",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Yellow,
                title = "Project T",
                date = "12/12/2025",
                onClick = {}
            )

            ProjectCard(
                colorIndicator = Color.Cyan,
                title = "Project A",
                date = "27/01/2026",
                onClick = {}
            )
        }
    }
}