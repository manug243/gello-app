package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.execute
import com.skash.forge.network.response.ApiResponse
import de.gello.data.mapper.toToken
import de.gello.data.mapper.toUser
import de.gello.data.network.endpoint.Api
import de.gello.data.network.request.LoginRequest
import de.gello.data.network.request.RegisterRequest
import de.gello.data.network.response.TokenResponse
import de.gello.data.network.response.UserResponse
import de.gello.domain.model.Token
import de.gello.domain.model.User
import de.gello.domain.repository.UserRepository

class UserRepositoryImpl(
    private val httpClient: HttpClient
) : UserRepository {

    override suspend fun fetchSelfUser(): ApiResponse<User> =
        httpClient.execute<UserResponse, User>(
            mapper = { it.toUser() },
            requestBuilder = {
                get(Api.Auth.User)
            }
        )

    override suspend fun authenticateUser(
        username: String,
        password: String
    ): ApiResponse<Token> =
        httpClient.execute<TokenResponse, Token>(
            mapper = { it.toToken() },
            requestBuilder = {
                post(Api.Auth.Login)
                body(LoginRequest(username = username, password = password))
            }
        )

    override suspend fun registerUser(
        username: String,
        password: String
    ): ApiResponse<Unit> =
        httpClient.execute<Unit, Unit>(
            requestBuilder = {
                post(Api.Auth.Register)
                body(RegisterRequest(username = username, password = password))
            },
            mapper = {}
        )


}