package ink.ddddd.flip.shared.domain.pref.onboarding

import ink.ddddd.flip.shared.data.pref.PreferenceStorage
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class GetOnBoardingState @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean {
        return preferenceStorage.onBoardingCompleted
    }

}