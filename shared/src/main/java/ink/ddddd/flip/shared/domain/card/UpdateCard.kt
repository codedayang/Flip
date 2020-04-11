package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class UpdateCard @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<Card, Unit>() {
    override fun execute(parameters: Card) {
        cardRepository.updateCard(parameters)
    }

}