package cc.foxa.flip.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.Result
import cc.foxa.flip.shared.domain.pref.onboarding.GetOnBoardingState
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
    private val getOnBoardingState: GetOnBoardingState
): ViewModel() {
    private val getOnBoardingStateResult = MutableLiveData<Result<Boolean>>()

    val launchDestination = getOnBoardingStateResult.map {
        if ((it as? Result.Success)?.data == false) {
            Event(LaunchDestination.ONBOARDING)
        } else {
            Event(LaunchDestination.MAIN_ACTIVITY)
        }
    }

    init {
        getOnBoardingState(viewModelScope, Unit, getOnBoardingStateResult)
    }


}

enum class LaunchDestination {
    ONBOARDING, MAIN_ACTIVITY
}