package de.gello.data.network.endpoint

import com.skash.forge.network.request.Route

internal object Api {

    object Auth : Route("BuildKonfig.BASE_URL", "auth") {
        object Login : Route(this, "login")
        object User : Route(this, "user")
        object Refresh : Route(this, "refresh")
    }
}