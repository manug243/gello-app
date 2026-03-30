package de.gello.app.feature.entryCreation

import de.gello.domain.model.GelEntry
import de.gello.domain.model.GelImage
import de.gello.util.enums.EntryEnum

data class EntryDraft(
    val title: String = "",
    val type: String? = null,
    val gelImage: GelImage? = null,
    val content: GelEntry? = null,
    val laneCount: String? = null
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
                val typeChosen = draft.type != null

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
            data class SetLaneCount(val value: String) : Intent
            data object ToSecondStep : Intent
            data object ToFourthStep : Intent
        }
    }

    data class FourthStep(
        override val draft: EntryDraft,
        val showTable: Boolean = false,
        val showError: Boolean = false
    ) : EntryCreationState() {
        sealed interface Intent : EntryCreationState.Intent {
            data class SetProbe(val lane: String, val value: String) : Intent
            data class SetVolume(val lane: String, val value: String) : Intent
            data class ShowDataTable(val value: Boolean) : Intent
            data object ToThirdStep : Intent
            data object SaveEntry : Intent
        }
    }
}