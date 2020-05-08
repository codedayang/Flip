package ink.ddddd.flip.rulebrowse

import androidx.lifecycle.*
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.rule.GetRules
import ink.ddddd.flip.shared.filter.TagFilter
import javax.inject.Inject

class RuleBrowseViewModel @Inject constructor(
    private val getRules: GetRules
) : ViewModel(), RuleBrowseActionHandler {
    val getRulesState = MutableLiveData<Int>(STATE_LOADING)

    private val getRulesResult = MutableLiveData<Result<List<Rule>>>()

    val rules = getRulesResult.map {
        when (it) {
            is Result.Success -> {
                getRulesState.value = STATE_SUCCESS
                it.data
            }
            else -> {
                getRulesState.value = STATE_FAILED
                null
            }
        }
    }
    init {
        getRules(viewModelScope, Unit, getRulesResult)
//        rules.value = listOf(Rule(name = "Test", filters = listOf(TagFilter(tags = listOf(Tag(name = "Bar"))))))
    }
    override fun openRuleEditor(rule: Rule) {
//        TODO("not implemented")
    }

    companion object {
        const val STATE_LOADING = 0x01
        const val STATE_SUCCESS = 0x02
        const val STATE_FAILED = 0x03
    }

}