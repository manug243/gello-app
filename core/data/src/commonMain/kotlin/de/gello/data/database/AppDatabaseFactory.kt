package de.gello.data.database

expect object AppDatabaseFactory {
    fun create(): AppDatabase
}