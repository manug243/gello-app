package de.gello.data.database

import android.content.Context
import androidx.room.Room
import org.koin.core.component.KoinComponent
import kotlin.getValue
import org.koin.core.component.inject

actual object AppDatabaseFactory : KoinComponent {

    private val appContext by inject<Context>()

    actual fun create(): AppDatabase =
        Room.databaseBuilder(
            appContext.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
}