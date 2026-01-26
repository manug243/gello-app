package de.gello.data.datastore

import com.skash.forge.datastore.DataEntry

object AppDataEntry {
    val UserID = DataEntry.int("user_id", 0)

    val AuthToken = DataEntry.string("auth_token", "")

    val RefreshToken = DataEntry.string("refresh_token", "")
}