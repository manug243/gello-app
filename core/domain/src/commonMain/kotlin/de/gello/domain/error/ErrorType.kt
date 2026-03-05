package de.gello.domain.error

import com.skash.forge.network.response.ApiResponse

sealed class ErrorType(
    open val message: String
) {
    data object UnauthorizedAccess : ErrorType(message = "Invalid credentials")

    data class Serialization(override val message: String) : ErrorType(message)

    data class Network(override val message: String) : ErrorType(message)

    data class Http(override val message: String) : ErrorType(message)

    data class Custom(override val message: String) : ErrorType(message = message)

    data object RegistrationFailed : ErrorType("Registration failed")

    data object FetchJournalsFailed : ErrorType("Fetch of journals failed")

    data object FetchEntriesFailed : ErrorType("Fetch of entries failed")

    data object FetchEntryFailed : ErrorType("Fetch of entry failed")

    data object CreateJournalFailed : ErrorType("Creation of journal failed")

    data object DeleteError : ErrorType("Error while trying to delete")
}

inline fun ApiResponse.Error.toErrorType(defaultErrorType: ApiResponse.Error.Unspecified.() -> ErrorType): ErrorType =
    when (this) {
        is ApiResponse.Error.HttpError -> ErrorType.Http(message = reason)
        is ApiResponse.Error.NetworkError -> ErrorType.Network(message = reason)
        is ApiResponse.Error.SerializationError -> ErrorType.Serialization(message = reason)
        is ApiResponse.Error.Unspecified -> defaultErrorType(this)
    }