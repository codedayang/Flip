package ink.ddddd.flip.shared.domain.pref.cardedit

import ink.ddddd.flip.shared.data.pref.PreferenceStorage
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class CardEditHelperSetShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
): UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.cardEditHelperShown = parameters
    }

}