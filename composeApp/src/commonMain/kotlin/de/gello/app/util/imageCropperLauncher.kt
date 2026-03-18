package de.gello.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.attafitamim.krop.core.crop.CropError
import com.attafitamim.krop.core.crop.CropResult
import com.attafitamim.krop.core.crop.CropState
import com.attafitamim.krop.core.crop.crop
import com.attafitamim.krop.core.crop.rememberImageCropper
import com.attafitamim.krop.filekit.encodeToByteArray
import com.attafitamim.krop.filekit.toImageSrc
import de.gello.domain.model.GelImage

import kotlinx.coroutines.launch

data class ImageCropperLauncher(
    val cropState: CropState?,
    val launch: (GelImage) -> Unit
)

@Composable
fun rememberImageCropperLauncher(
    onCropCancelled: () -> Unit,
    onCropFailed: (Throwable?) -> Unit = {},
    onCropSuccess: (GelImage) -> Unit
): ImageCropperLauncher {
    val scope = rememberCoroutineScope()
    val imageCropper = rememberImageCropper()

    val launchCrop: (GelImage) -> Unit = { image ->
        scope.launch {
            val imageSrc = image.file?.toImageSrc()

            when (val result = imageCropper.crop(imageSrc)) {
                CropResult.Cancelled -> onCropCancelled()

                is CropError -> onCropFailed(null)

                is CropResult.Success -> {
                    val croppedBytes = result.bitmap.encodeToByteArray()
                    onCropSuccess(
                        image.copy(data = croppedBytes)
                    )
                }
            }
        }
    }

    return ImageCropperLauncher(
        cropState = imageCropper.cropState,
        launch = launchCrop
    )
}