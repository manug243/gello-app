package de.gello.data.network.endpoint

import com.skash.forge.network.request.HttpHeader

internal sealed class HeaderValue(
    override val key: String,
    override val value: String,
) : HttpHeader {
    data class Bearer(
        val bearer: String,
    ) : HeaderValue(key = "Authorization", value = "Bearer $bearer")
}