package cc.foxa.flip.shared.domain.card

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.RuleSet
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class GetCards @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<RuleSet, List<Card>>() {
    override fun execute(parameters: RuleSet): List<Card> {
        return cardRepository.getCards(parameters)
    }
}