package cc.foxa.flip.shared.domain.card

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.model.RuleSet
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class RefreshCardCache @Inject constructor(
    private val cardRepository: CardRepository
) : UseCase<RuleSet, Unit>() {
    override fun execute(parameters: RuleSet) {
        cardRepository.refreshCache(parameters)
    }

}