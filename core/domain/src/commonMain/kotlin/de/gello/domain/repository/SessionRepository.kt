package de.gello.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun getUserID(): Result<Int>
    suspend fun getAuthToken(): Result<String>
    suspend fun getRefreshToken(): Result<String>

    suspend fun setUserID(userID: Int)
    suspend fun setAuthToken(token: String)
    suspend fun setRefreshToken(token: String)

    suspend fun deleteUserID()
    suspend fun deleteAuthToken()
    suspend fun deleteRefreshToken()

    fun observeAuthToken(): Flow<String>
}