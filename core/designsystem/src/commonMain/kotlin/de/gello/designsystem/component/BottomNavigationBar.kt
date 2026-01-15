package de.gello.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

@Composable
fun <T> BottomNavigationBar(
    items: List<T>,
    selectedItem: T,
    icon: @Composable RowScope.(item: T) -> Unit,
    label: @Composable RowScope.(item: T) -> Unit,
    onItemSelect: (T) -> Unit,
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { icon(item) },
                label = { label(item) },
                selected = item == selectedItem,
                onClick = {
                    onItemSelect(item)
                },
            )
        }
    }
}