package ink.ddddd.flip.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.domain.pref.onboarding.GetOnBoardingState
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