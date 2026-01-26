package de.gello.data.di

import de.gello.data.database.AppDatabase
import de.gello.data.database.AppDatabaseFactory
import de.gello.data.database.dao.UserDao
import de.gello.data.repository.SessionRepositoryImpl
import de.gello.data.repository.UserRepositoryImpl
import de.gello.domain.repository.SessionRepository
import de.gello.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<SessionRepository> { SessionRepositoryImpl(get()) }
    single<AppDatabase> { AppDatabaseFactory.create() }
    single<UserDao> { get<AppDatabase>().userDao() }
}