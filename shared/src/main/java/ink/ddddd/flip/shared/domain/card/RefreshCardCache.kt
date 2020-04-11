package ink.ddddd.flip.shared.domain.card

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class RefreshCardCache @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<RuleSet, Unit>() {
    override fun execute(parameters: RuleSet) {
        cardRepository.refreshCache(parameters)
    }

}