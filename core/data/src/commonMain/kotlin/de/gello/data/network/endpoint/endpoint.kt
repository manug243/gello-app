package de.gello.data.network.endpoint

import com.skash.forge.network.request.Route
import de.gello.BuildKonfig
import de.gello.BuildKonfig.BASE_URL

internal object Api {

    object Auth : Route(BASE_URL, "auth") {
        object Login : Route(this, "login")
        object User : Route(this, "user")
        object Refresh : Route(this, "refresh")
        object Register : Route(this, "register")
    }

    object Project : Route(BASE_URL, "project") {

    }
}