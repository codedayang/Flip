package cc.foxa.flip.shared.domain.card

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class UpdateCard @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<Card, Unit>() {
    override fun execute(parameters: Card) {
        cardRepository.updateCard(parameters)
    }

}