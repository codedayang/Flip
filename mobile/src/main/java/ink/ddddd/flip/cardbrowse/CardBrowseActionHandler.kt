package ink.ddddd.flip.cardbrowse

import ink.ddddd.flip.shared.data.model.Card

interface CardBrowseActionHandler {
    fun openCardEditor(card: Card)
}