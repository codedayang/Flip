package cc.foxa.flip.shared.domain.card

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class SearchCard @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<String, List<Card>>() {
    override fun execute(parameters: String): List<Card> {
        return cardRepository.searchCard(parameters)
    }

}