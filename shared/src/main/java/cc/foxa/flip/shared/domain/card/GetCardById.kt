package cc.foxa.flip.shared.domain.card

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.NoSuchCardException
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.domain.UseCase
import java.lang.NullPointerException
import javax.inject.Inject

class GetCardById @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<String, Card>() {
    override fun execute(parameters: String): Card {
        try {
            return cardRepository.getCardById(parameters)
        } catch (e: NullPointerException) {
            throw NoSuchCardException()
        }
    }

}