package de.gello.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Doorbell
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.skash.forge.event.EventBus
import com.skash.forge.navigation.nav2.DefaultNavHost
import de.gello.app.event.UIEvent
import de.gello.app.event.UIEventHandler
import de.gello.app.navigation.BottomNavItem
import de.gello.app.navigation.TopLevelScreen
import de.gello.app.navigation.topLevelGraph
import de.gello.designsystem.component.BottomNavigationBar
import de.gello.designsystem.component.FAB
import de.gello.designsystem.component.ScreenScaffold
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewmodel: AppViewModel) {
    val state by viewmodel.collectStateFlow().collectAsState()
    val navController = rememberNavController()
    val eventBus = koinInject<EventBus<UIEvent>>()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val onTabClicked: (BottomNavItem) -> Unit = { tab ->
        navController.navigate(tab.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    val tabs = listOf(
        BottomNavItem("Overview", TopLevelScreen.Overview, Icons.Default.Book),
        BottomNavItem("Example", TopLevelScreen.Example, Icons.Default.Doorbell)
    )

    val selectedTab = tabs.findSelectedTabForDestination(currentDestination)

    val tabTitle = selectedTab.title

    val isOverviewScreen = selectedTab.route == TopLevelScreen.Overview

    UIEventHandler(
        uiEvents = eventBus.events
    ) { snackBarHost ->

        ScreenScaffold(
            topBar = {
                TopAppBar(
                    title = { Text(tabTitle) }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = tabs,
                    selectedItem = tabs.findSelectedTabForDestination(currentDestination),
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = it.title)
                    },
                    onItemSelect = onTabClicked
                )
            },
            snackbarHost = snackBarHost,
            floatingActionButton = {
                AnimatedVisibility(visible = isOverviewScreen) {
                    FAB(
                        onClick = { viewmodel.executeIntent(AppState.Intent.NavigateToCreateJournal) }
                    )
                }
            }
        ) {
            DefaultNavHost(
                navController = navController,
                startDestination = TopLevelScreen.Overview
            ) {
                topLevelGraph()
            }
        }
    }
}

private fun List<BottomNavItem>.findSelectedTabForDestination(currentDestination: NavDestination?): BottomNavItem =
    find { tab ->
        currentDestination?.hierarchy?.any { it.route == tab.route::class.qualifiedName } == true
    } ?: first()