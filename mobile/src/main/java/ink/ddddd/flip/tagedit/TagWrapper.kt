package ink.ddddd.flip.tagedit

import ink.ddddd.flip.shared.data.model.Tag

data class TagWrapper(
    val tag: Tag,
    val isChecked: Boolean = false
)