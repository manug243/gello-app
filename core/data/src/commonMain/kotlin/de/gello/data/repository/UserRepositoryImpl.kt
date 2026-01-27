package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.Token
import de.gello.domain.model.User
import de.gello.domain.repository.UserRepository

class UserRepositoryImpl(
    private val httpClient: HttpClient
) : UserRepository {

    // for testing
    override suspend fun fetchSelfUser(): ApiResponse<User> {
        return ApiResponse.Success(
            User(
                id = 1,
                email = "TestUser@gello.com"
            )
        )
    }

    // for testing
    override suspend fun authenticateUser(
        email: String,
        password: String
    ): ApiResponse<Token> {
        return if (email == "admin" && password == "admin") {
            ApiResponse.Success(
                Token(
                    token = "fake-access-token",
                    refreshToken = "fake-refresh-token"
                )
            )
        } else {
            ApiResponse.Error.Unspecified(
                code = 500,
                reason = "Test, wrong input"
            )
        }
    }
}