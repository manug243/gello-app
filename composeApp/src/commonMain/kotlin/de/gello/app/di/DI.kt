package de.gello.app.di

import coil3.ImageLoader
import com.russhwolf.settings.ExperimentalSettingsApi
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
import de.gello.app.feature.settings.SettingsViewModel
import de.gello.app.feature.auth.login.LoginViewModel
import de.gello.app.feature.auth.registration.RegistrationViewModel
import de.gello.app.feature.entryCreation.EntryCreationViewModel
import de.gello.app.feature.entryDetail.EntryDetailViewModel
import de.gello.app.feature.journalCreation.JournalCreationViewModel
import de.gello.app.feature.journalDetail.JournalDetailViewModel
import de.gello.app.feature.overview.OverviewViewModel
import de.gello.data.session.SessionManager
import de.gello.data.session.TokenAuthenticatorImpl
import de.gello.domain.usecase.FetchUserUseCase
import de.gello.domain.usecase.LoginUseCase
import de.gello.domain.usecase.LogoutUseCase
import de.gello.domain.usecase.ObserveIsUserLoggedInUseCase
import de.gello.domain.usecase.RegisterUserUseCase
import de.gello.domain.usecase.entry.DeleteEntryUseCase
import de.gello.domain.usecase.entry.FetchOneEntryUseCase
import de.gello.domain.usecase.image.UploadGelImageUseCase
import de.gello.domain.usecase.journal.CreateJournalUseCase
import de.gello.domain.usecase.journal.DeleteJournalUseCase
import de.gello.domain.usecase.journal.FetchJournalsUseCase
import de.gello.domain.usecase.journal.FetchOneJournalUseCase
import io.github.vinceglb.filekit.coil.addPlatformFileSupport
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@OptIn(ExperimentalSettingsApi::class)
val appModule = module {

    viewModelOf(::AppViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::OverviewViewModel)
    viewModelOf(::JournalCreationViewModel)
    viewModelOf(::JournalDetailViewModel)
    viewModelOf(::EntryDetailViewModel)
    viewModelOf(::EntryCreationViewModel)

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
    factory { RegisterUserUseCase(get()) }
    factory { FetchUserUseCase(get()) }
    factory { FetchJournalsUseCase(get()) }
    factory { CreateJournalUseCase(get()) }
    factory { FetchOneJournalUseCase(get()) }
    factory { FetchOneEntryUseCase(get()) }
    factory { DeleteEntryUseCase(get()) }
    factory { DeleteJournalUseCase(get()) }
    factory { UploadGelImageUseCase(get()) }
}