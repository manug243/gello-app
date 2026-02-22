package de.gello.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

fun main() = application {

    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val width = (screenSize.width * 0.2).toInt()
    val height = (screenSize.height * 0.6).toInt()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Gello",
        resizable = false,
        state = rememberWindowState(
            width = width.dp,
            height = height.dp
        )
    ) {
        GelloApp()
    }
}