package de.gello.data.repository

import com.skash.forge.network.client.HttpClient
import com.skash.forge.network.client.execute
import com.skash.forge.network.response.ApiResponse
import de.gello.data.mapper.toGelEntry
import de.gello.data.network.endpoint.Api
import de.gello.data.network.response.GelResponse
import de.gello.domain.model.GelEntry
import de.gello.domain.model.GelImage
import de.gello.domain.repository.GelImageRepository

class GelImageRepositoryImpl(
    private val httpClient: HttpClient
) : GelImageRepository {
    override suspend fun uploadImage(gelImage: GelImage): ApiResponse<GelEntry> =
        httpClient.execute<GelResponse, GelEntry>(
            requestBuilder = {
                post(Api.GelImage)
                multipartBody {
                    addFile(
                        name = "image",
                        filename = gelImage.name,
                        content = gelImage.data,
                        contentType = "image/${gelImage.fileType}"
                    )
                }
            },
            mapper = { it.toGelEntry() }
        )
}