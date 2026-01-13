package de.gello.app.event

sealed interface UIEvent {
    data class Snackbar(val message: String) : UIEvent
}