package cc.foxa.flip.tagedit

import cc.foxa.flip.shared.data.model.Tag

data class TagWrapper(
    val tag: Tag,
    val isChecked: Boolean = false
)