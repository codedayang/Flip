package ink.ddddd.flip.cardbrowse

import androidx.lifecycle.*
import ink.ddddd.flip.perform.FORMULA
import ink.ddddd.flip.perform.LONG_TEXT
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.GetCards
import ink.ddddd.flip.shared.domain.card.SearchCard
import ink.ddddd.flip.shared.domain.rule.GetRules
import ink.ddddd.flip.shared.domain.tag.GetTags
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import ink.ddddd.flip.shared.filter.Filter
import ink.ddddd.flip.shared.filter.TagFilter
import ink.ddddd.flip.shared.util.noRule
import ink.ddddd.flip.shared.util.noRuleSet
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CardBrowseViewModel @Inject constructor(
    private val getCards: GetCards,
    private val getTags: GetTags,
    private val getRules: GetRules
) : ViewModel(), CardBrowseActionHandler {

    val loadCardState = MutableLiveData<Int>(STATE_LOADING)

    val loadCardsResult = MutableLiveData<Result<List<Card>>>()

    val cards = loadCardsResult.map {
        when (it) {
            is Result.Success -> {
                loadCardState.value = STATE_SUCCESS
                it.data
            }
            else -> {
                loadCardState.value = STATE_FAILED
                null
            }
        }
    }

    val loadTagsState = MutableLiveData<Int>(STATE_LOADING)

    val loadTagsResult = MutableLiveData<Result<List<Tag>>>()

    val tags = loadTagsResult.map {
        when (it) {
            is Result.Success -> {
                loadTagsState.value = STATE_SUCCESS
                it.data
            }
            else -> {
                loadTagsState.value = STATE_FAILED
                listOf<Tag>()
            }
        }
    }

    val loadRulesState = MutableLiveData<Int>(STATE_LOADING)

    val loadRulesResult = MutableLiveData<Result<List<Rule>>>()

    val rules= loadRulesResult.map {
        when (it) {
            is Result.Success -> {
                loadRulesState.value = STATE_SUCCESS
                it.data
            }
            else -> {
                loadRulesState.value = STATE_FAILED
                listOf<Rule>()
            }
        }
    }




    val navigateToEditor = MutableLiveData<Event<String>>()


    val tempRuleFilters = mutableListOf<Filter>()

    val refreshTempRuleFilter = MutableLiveData<Event<Unit>>()

    var curRuleSet = noRuleSet()

    init {

        getCards(viewModelScope, noRuleSet(), loadCardsResult)
        getTags(viewModelScope, Unit, loadTagsResult)
        getRules(viewModelScope, Unit, loadRulesResult)
    }

    fun applyRuleSet(ruleSet: RuleSet) {
        curRuleSet = ruleSet
        getCards(viewModelScope, ruleSet, loadCardsResult)
    }

    fun refresh() {
        getCards(viewModelScope, curRuleSet, loadCardsResult)
        getTags(viewModelScope, Unit, loadTagsResult)
        getRules(viewModelScope, Unit, loadRulesResult)

    }

    companion object {
        const val STATE_LOADING = 0x01
        const val STATE_FAILED = 0x02
        const val STATE_SUCCESS = 0x03
    }

    override fun openCardEditor(card: Card) {
        navigateToEditor.value = Event(card.id)
    }
}