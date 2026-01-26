package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Token
import de.gello.domain.model.User
import de.gello.domain.repository.UserRepository

class UserRepositoryImpl(
    private val httpClient: HttpClient
) : UserRepository {
    override suspend fun fetchSelfUser(): ApiResponse<User> {
        TODO("Not yet implemented")
    }

    override suspend fun authenticateUser(
        email: String,
        password: String
    ): ApiResponse<Token> {
        TODO("Not yet implemented")
    }
}