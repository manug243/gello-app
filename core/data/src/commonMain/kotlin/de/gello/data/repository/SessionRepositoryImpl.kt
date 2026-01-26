package de.gello.data.repository

import com.skash.forge.datastore.DataStore
import de.gello.data.datastore.AppDataEntry
import de.gello.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(
    private val dataStore: DataStore
) : SessionRepository {
    override suspend fun getUserID(): Result<Int> {
        return dataStore.get(AppDataEntry.UserID).toResult()
    }

    override suspend fun getAuthToken(): Result<String> {
        return dataStore.get(AppDataEntry.AuthToken).toResult()
    }

    override suspend fun getRefreshToken(): Result<String> {
        return dataStore.get(AppDataEntry.RefreshToken).toResult()
    }

    override suspend fun setUserID(userID: Int) {
        dataStore.set(AppDataEntry.UserID, userID)
    }

    override suspend fun setAuthToken(token: String) {
        dataStore.set(AppDataEntry.AuthToken, token)
    }

    override suspend fun setRefreshToken(token: String) {
        dataStore.set(AppDataEntry.RefreshToken, token)
    }

    override suspend fun deleteUserID() {
        dataStore.delete(AppDataEntry.UserID)
    }

    override suspend fun deleteAuthToken() {
        dataStore.delete(AppDataEntry.AuthToken)
    }

    override suspend fun deleteRefreshToken() {
        dataStore.delete(AppDataEntry.RefreshToken)
    }

    override fun observeAuthToken(): Flow<String> {
        return dataStore.observe(AppDataEntry.AuthToken)
    }

    fun <T> T?.toResult(
        exception: Throwable = NullPointerException("Value was null")
    ): Result<T> {
        return if (this != null) {
            Result.success(this)
        } else {
            Result.failure(exception)
        }
    }
}