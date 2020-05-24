package cc.foxa.flip.shared.domain.pref.cardedit

import cc.foxa.flip.shared.data.pref.PreferenceStorage
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class CardEditHelperIsShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
): UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean {
        return preferenceStorage.cardEditHelperShown
    }

}