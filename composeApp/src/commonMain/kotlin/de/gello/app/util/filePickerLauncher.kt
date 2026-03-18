package de.gello.app.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import de.gello.domain.model.GelImage
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.PickerResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.mimeType
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

@Composable
fun rememberFilePicker(
    onFileSelected: (GelImage) -> Unit
): PickerResultLauncher {
    val scope = rememberCoroutineScope()

    return rememberFilePickerLauncher(
        mode = FileKitMode.Single,
        type = FileKitType.Image,
    ) { file ->
        file?.let {
            scope.launch {
                onFileSelected(
                    GelImage(
                        file = file,
                        data = file.readBytes(),
                        name = file.name,
                        fileType = file.extension
                    )
                )
            }
        }
    }
}