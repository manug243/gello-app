package de.gello.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CoveringProgressIndicator(modifier: Modifier = Modifier) {
    Surface {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .then(modifier),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProgressIndicator()
        }
    }
}