package de.gello.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import gello.composeapp.generated.resources.Res
import gello.composeapp.generated.resources.appicon
import org.jetbrains.compose.resources.painterResource
import java.awt.Toolkit

fun main() = application {

    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val width = (screenSize.width * 0.25).toInt()
    val height = (screenSize.height * 0.7).toInt()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Gello",
        resizable = false,
        state = rememberWindowState(
            width = width.dp,
            height = height.dp
        ),
        icon = painterResource(Res.drawable.appicon)
    ) {
        GelloApp()
    }
}