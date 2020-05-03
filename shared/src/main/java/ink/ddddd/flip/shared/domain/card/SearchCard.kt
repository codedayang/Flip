package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class SearchCard @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<String, List<Card>>() {
    override fun execute(parameters: String): List<Card> {
        return cardRepository.searchCard(parameters)
    }

}