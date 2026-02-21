package de.gello.data.database

import androidx.room.Room
import org.koin.core.component.KoinComponent
import java.io.File

actual object AppDatabaseFactory : KoinComponent {


    actual fun create(): AppDatabase {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "app_database")

        return Room.databaseBuilder<AppDatabase>(
            dbFile.absolutePath
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}