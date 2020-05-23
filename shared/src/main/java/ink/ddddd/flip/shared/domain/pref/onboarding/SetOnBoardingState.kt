package ink.ddddd.flip.shared.domain.pref.onboarding

import ink.ddddd.flip.shared.data.pref.PreferenceStorage
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class SetOnBoardingState @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.onBoardingCompleted = parameters
    }

}