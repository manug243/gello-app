package de.gello.app.di

import com.skash.forge.datastore.DataStore
import com.skash.forge.datastore.multiplatformsettings.MultiplatformSettingsDataStore
import com.skash.forge.event.DefaultEventBus
import com.skash.forge.event.EventBus
import com.skash.forge.navigation.NavigationDispatcher
import com.skash.forge.navigation.nav2.DefaultNavigationDispatcher
import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.HttpClientBundle
import com.skash.forge.network.client.StateClearable
import com.skash.forge.network.ktor.KtorApiClient
import com.skash.forge.network.session.SessionExpirationHandler
import com.skash.forge.network.session.TokenAuthenticator
import de.gello.app.AppViewModel
import de.gello.app.event.UIEvent
import de.gello.app.feature.example.ExampleViewModel
import de.gello.app.feature.login.LoginViewModel
import de.gello.app.feature.overview.OverviewViewModel
import de.gello.data.session.SessionManager
import de.gello.data.session.TokenAuthenticatorImpl
import de.gello.domain.usecase.LoginUseCase
import de.gello.domain.usecase.LogoutUseCase
import de.gello.domain.usecase.ObserveIsUserLoggedInUseCase
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::AppViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ExampleViewModel)
    viewModelOf(::OverviewViewModel)

    single<EventBus<UIEvent>> { DefaultEventBus() }
    single<SessionExpirationHandler> { SessionManager(get()) }
    single<NavigationDispatcher> { DefaultNavigationDispatcher() }
    single<TokenAuthenticator> { TokenAuthenticatorImpl(get(), get()) }
    single<DataStore> { MultiplatformSettingsDataStore() }
    single<HttpClientBundle> {
        KtorApiClient {
            authentication(get())
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    explicitNulls = true
                    coerceInputValues = true
                }
            )
        }
    }
    single<HttpClient> {
        get<HttpClientBundle>().client
    }
    single<StateClearable> {
        get<HttpClientBundle>().stateClearable
    }

    factory { ObserveIsUserLoggedInUseCase(get()) }
    factory { LoginUseCase(get(), get()) }
    factory { LogoutUseCase(get(), get()) }
}