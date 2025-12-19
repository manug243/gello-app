package de.gello.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform