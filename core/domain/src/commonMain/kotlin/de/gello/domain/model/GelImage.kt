package de.gello.domain.model

import io.github.vinceglb.filekit.PlatformFile

data class GelImage(
    val file: PlatformFile? = null,
    val data: ByteArray,
    val name: String,
    val fileType: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GelImage

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}