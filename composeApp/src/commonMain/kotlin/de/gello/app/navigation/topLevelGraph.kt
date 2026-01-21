package de.gello.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.skash.forge.navigation.nav2.composableWithTransition
import de.gello.app.feature.example.ExampleScreen

fun NavGraphBuilder.topLevelGraph() {
    // here we are going to register the actual top level screens, like actual tab screens ("first screens") maybe Dairy-tab
    // example:
    // composableWithTransition<TopLevelScreen.Diary> {
    //        DairyScreen()
    //    }

    composableWithTransition<TopLevelScreen.Example> {
        ExampleScreen()
    }
}