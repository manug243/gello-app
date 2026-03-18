package de.gello.app.feature.entryCreation

import de.gello.domain.model.GelImage
import de.gello.util.enums.EntryEnum

data class EntryDraft(
    val title: String = "",
    val typeId: Int? = null,
    val gelImage: GelImage? = null,
    val content: String? = "" // will be a json
)

sealed class EntryCreationState {

    open val draft: EntryDraft? = null

    sealed interface Intent {
        data object NavigateUp : Intent
    }

    data object Loading : EntryCreationState()

    data class FirstStep(
        override val draft: EntryDraft,
        val showError: Boolean = false
    ) : EntryCreationState() {
        sealed interface Intent : EntryCreationState.Intent {
            data class SetEntryTitle(val value: String) : Intent
            data class SetEntryType(val value: EntryEnum) : Intent
            data object ToSecondStep : Intent
        }

        val entryOptions = EntryEnum.entries.filter { it != EntryEnum.UNKNOWN }

        val allFirstFieldsFilled: Boolean
            get() {
                val titleFilled = draft.title.isNotBlank()
                val typeChosen = draft.typeId != null

                return titleFilled && typeChosen
            }
    }

    data class SecondStep(
        override val draft: EntryDraft,
        val showError: Boolean = false
    ) : EntryCreationState() {
        sealed interface Intent : EntryCreationState.Intent {
            data object ToFirstStep : Intent
            data class ToThirdStepWithUpload(val value: GelImage) : Intent
            data object CancelCrop : Intent
            data class SelectImage(val value: GelImage) : Intent
        }

        val imageSelected: Boolean
            get() {
                return draft.gelImage != null
            }
    }

    data class ThirdStep(
        override val draft: EntryDraft,
        val optimizedImage: GelImage? = null,
        val showError: Boolean = false
    ) : EntryCreationState() {
        sealed interface Intent : EntryCreationState.Intent {
            data object ToSecondStep : Intent
            data object ToFourthStep : Intent
        }
    }
}