package de.gello.app.di

import com.skash.forge.event.DefaultEventBus
import com.skash.forge.event.EventBus
import de.gello.app.AppViewModel
import de.gello.app.event.UIEvent
import de.gello.app.feature.example.ExampleViewModel
import de.gello.domain.usecase.LoginUseCase
import de.gello.domain.usecase.ObserveIsUserLoggedInUseCase
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::AppViewModel)
    viewModelOf(::ExampleViewModel)

    single<EventBus<UIEvent>> { DefaultEventBus() }

    factory { ObserveIsUserLoggedInUseCase(get()) }
    factory { LoginUseCase(get(), get()) }
}