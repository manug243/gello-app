package de.gello.domain.repository

import com.skash.forge.network.response.ApiResponse
import de.gello.domain.model.GelImage

interface GelImageRepository {

    suspend fun uploadImage(gelImage: GelImage) : ApiResponse<ByteArray>
}