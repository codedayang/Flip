package cc.foxa.flip.cardbrowse

import cc.foxa.flip.shared.data.model.Card

interface CardBrowseActionHandler {
    fun openCardEditor(card: Card)
}