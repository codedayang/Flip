package cc.foxa.flip.cardbrowse

import androidx.lifecycle.*
import cc.foxa.flip.perform.FORMULA
import cc.foxa.flip.perform.LONG_TEXT
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.Result
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.model.RuleSet
import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.domain.card.GetCards
import cc.foxa.flip.shared.domain.card.SearchCard
import cc.foxa.flip.shared.domain.rule.GetRules
import cc.foxa.flip.shared.domain.tag.GetTags
import cc.foxa.flip.shared.domain.tag.UpdateTag
import cc.foxa.flip.shared.filter.Filter
import cc.foxa.flip.shared.filter.TagFilter
import cc.foxa.flip.shared.util.noRule
import cc.foxa.flip.shared.util.noRuleSet
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