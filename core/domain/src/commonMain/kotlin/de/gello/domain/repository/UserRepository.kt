package de.gello.domain.repository

import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Token
import de.gello.domain.model.User

interface UserRepository {
    suspend fun fetchSelfUser(): ApiResponse<User>

    suspend fun authenticateUser(email: String, password: String): ApiResponse<Token>
}