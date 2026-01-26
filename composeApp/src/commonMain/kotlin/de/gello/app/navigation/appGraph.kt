package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.example.ExampleScreen
import de.gello.app.feature.example.ExampleViewModel
import org.koin.compose.koinInject

fun NavGraphBuilder.appGraph() {
    // here we are going to register the actual non-top level feature related screens, like detail screen
    // example:
    // composableWithTransition<Screen.DiaryDetail> {
    //        DiaryDetailsScreen()
    //    }

    composableWithTransition<Screen.Example> {
        val viewModel = koinInject<ExampleViewModel>()
        ExampleScreen(viewmodel = viewModel)
    }
}