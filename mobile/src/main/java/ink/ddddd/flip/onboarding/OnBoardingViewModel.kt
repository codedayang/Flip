package ink.ddddd.flip.onboarding

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.demo.ImportDemo
import ink.ddddd.flip.shared.domain.pref.onboarding.SetOnBoardingState
import ink.ddddd.flip.shared.domain.rule.UpdateRule
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val setOnBoardingState: SetOnBoardingState,
    private val importDemo: ImportDemo
) : ViewModel() {
    val importDemoState = MediatorLiveData<ImportDemoState>()

    init {
        importDemoState.value = ImportDemoState.NOT_IMPORT
        setOnBoardingState(viewModelScope, true)
    }

    fun demo() {
        importDemoState.value = ImportDemoState.IMPORTING
        importDemoState.addSource(importDemo(viewModelScope, Unit)) {
            importDemoState.value = ImportDemoState.IMPORT_SUCCESS
        }
    }
}

enum class ImportDemoState {
    NOT_IMPORT, IMPORTING, IMPORT_SUCCESS
}