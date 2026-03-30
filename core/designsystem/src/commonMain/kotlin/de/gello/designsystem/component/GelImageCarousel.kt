package de.gello.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.gello.designsystem.theme.Spacing

@Composable
fun GelImageCarousel(
    originalImage: ByteArray,
    processedImage: ByteArray,
    modifier: Modifier = Modifier
) {
    val pages = listOf(
        "Processed image" to processedImage,
        "Original image" to originalImage
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            ImagePreview(byteArray = pages[page].second)
        }

        PagerDots(
            totalDots = pages.size,
            selectedIndex = pagerState.currentPage,
            modifier = Modifier.padding(top = Spacing.Small)
        )
    }
}