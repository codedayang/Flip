package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.NoSuchCardException
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.domain.UseCase
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