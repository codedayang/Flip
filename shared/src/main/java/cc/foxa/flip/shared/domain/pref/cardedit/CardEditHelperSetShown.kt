package cc.foxa.flip.shared.domain.pref.cardedit

import cc.foxa.flip.shared.data.pref.PreferenceStorage
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class CardEditHelperSetShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
): UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.cardEditHelperShown = parameters
    }

}