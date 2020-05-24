package cc.foxa.flip.onboarding

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.domain.card.UpdateCard
import cc.foxa.flip.shared.domain.demo.ImportDemo
import cc.foxa.flip.shared.domain.pref.onboarding.SetOnBoardingState
import cc.foxa.flip.shared.domain.rule.UpdateRule
import cc.foxa.flip.shared.domain.tag.UpdateTag
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