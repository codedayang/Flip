package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class GetNextCard @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<Pair<RuleSet, Boolean>, Card>() {

    /**
     * @param parameters pair's second item means whether to have a delay for a smooth animation
     */
    override fun execute(parameters: Pair<RuleSet, Boolean>): Card {
//        return Card(front = "Foo", back = "Bar")
        if (parameters.second) Thread.sleep(130L)
        return cardRepository.getNextCard(parameters.first)
    }

}