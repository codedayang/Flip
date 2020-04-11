package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class GetCards @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<RuleSet, List<Card>>() {
    override fun execute(parameters: RuleSet): List<Card> {
        return cardRepository.getCards(parameters)
    }
}