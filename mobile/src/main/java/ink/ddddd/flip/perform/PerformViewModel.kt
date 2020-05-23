package ink.ddddd.flip.perform

import androidx.lifecycle.*
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.domain.card.GetNextCard
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.rule.GetRules
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.data.source.CardTagDao
import ink.ddddd.flip.shared.data.source.RuleFilterDao
import ink.ddddd.flip.shared.domain.pref.perform.RuleSelectHelperIsShown
import ink.ddddd.flip.shared.domain.pref.perform.RuleSelectHelperSetShown
import ink.ddddd.flip.shared.domain.rule.UpdateRule
import ink.ddddd.flip.shared.domain.tag.GetTags
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import ink.ddddd.flip.shared.filter.TagFilter
import ink.ddddd.flip.shared.util.noRuleSet
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class PerformViewModel @Inject constructor(
    private val getNextCard: GetNextCard,
    private val updateCard: UpdateCard,
    private val getRules: GetRules,
    private val ruleSelectHelperIsShown: RuleSelectHelperIsShown,
    private val ruleSelectHelperSetShown: RuleSelectHelperSetShown

) : ViewModel() {

    val cardState = MutableLiveData<Int>(CARD_STATE_LOADING)

    val getNextCardResult = MutableLiveData<Result<Card>>()

    val card:LiveData<Card> = Transformations.map(getNextCardResult) {
        when (it) {
            is Result.Success -> {
                cardState.value = CARD_STATE_FRONT
                it.data
            }
            is Result.Error -> {
                cardState.value = CARD_STATE_FAILED
                null
            }
        }
    }

    val ruleState = MutableLiveData<Int>(RULE_STATE_LOADING)

    val getRulesResult = MutableLiveData<Result<List<Rule>>>()

    val rules: LiveData<List<Rule>> = Transformations.map(getRulesResult) {
        when (it) {
            is Result.Success -> {
                ruleState.value = RULE_STATE_SUCCESS
                it.data
            }
            is Result.Error -> {
                ruleState.value = RULE_STATE_FAILED
                null
            }
        }
    }

    val ruleSet = MutableLiveData<RuleSet>(noRuleSet())

    val executePendingBinding = MutableLiveData<Event<Unit>>()

    val resetCardScroller = MutableLiveData<Event<Unit>>()

    val snackbar = MutableLiveData<Event<String>>()

    val showRuleSelectHelper = MediatorLiveData<Event<Unit>>()

    init {

        // TestCode
//        runBlocking {
//            val tag1 = Tag(name = "BBBB")
//            updateTag.executeNow(tag1)
//            val tag2 = Tag(name = "AAA")
//            updateTag.executeNow(tag2)
//            updateCard.executeNow(Card(front = FORMULA, back = LONG_TEXT, tags = listOf(tag1)))
//            updateCard.executeNow(Card(front = "FooTag2", back = "FooTag2", tags = listOf(tag1,tag2)))
//
//            val tagFilter = TagFilter(tags = listOf(tag1))
//            val rule = Rule(name = "Tag:BBBB", filters = listOf(tagFilter))
//            updateRule.executeNow(rule)
//
//            val tagFilter2 = TagFilter(tags = listOf(tag2))
//            val rule2 = Rule(name = "Tag:AAA", filters = listOf(tagFilter2))
//            updateRule.executeNow(rule2)
//
//        }
        getNextCard(viewModelScope, ruleSet.value!! to true, getNextCardResult)
        getRules(viewModelScope, Unit, getRulesResult)

    }

    fun flip() {
        cardState.value = CARD_STATE_BACK
//        snackbar.value = Event("测试SnackBar")
    }

    fun feedback(priorityDelta: Int) {
        val t = card.value!!
        t.priority = t.priority + priorityDelta
        updateCard(viewModelScope, t)
        getNextCard(viewModelScope, ruleSet.value!! to true, getNextCardResult)
        cardState.value = CARD_STATE_LOADING
        executePendingBinding.value = Event(Unit)
        resetCardScroller.value = Event(Unit)
    }

    fun loadRules() {
        ruleState.value = RULE_STATE_LOADING
        getRules(viewModelScope, Unit, getRulesResult)
    }

    fun loadCard() {
        cardState.value = CARD_STATE_LOADING
        getNextCard(viewModelScope, ruleSet.value!! to true, getNextCardResult)
    }

    fun applyRuleSet(ruleSet: RuleSet) {
        this.ruleSet.value = ruleSet
        loadCard()
    }

    fun shouldShowRuleSelectHelper() {
        showRuleSelectHelper.addSource(ruleSelectHelperIsShown(viewModelScope, Unit)) {
            if (!(it as? Result.Success)?.data!!) {
                showRuleSelectHelper.value = Event(Unit)
                ruleSelectHelperSetShown(viewModelScope, true)
            }
        }
    }

    companion object {
        const val CARD_STATE_LOADING = 0
        const val CARD_STATE_FAILED = 1
        const val CARD_STATE_FRONT = 2
        const val CARD_STATE_BACK = 3

        const val RULE_STATE_LOADING = 0
        const val RULE_STATE_FAILED = 1
        const val RULE_STATE_SUCCESS = 2
    }
}

const val FORMULA = "中文测试 $$ foo $$ $$ c = \\pm\\sqrt{a^2 + b^2} $$"
const val LONG_TEXT = "晋太元中，武陵人捕鱼为业。缘溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，芳草鲜美，落英缤纷。渔人甚异之，复前行，欲穷其林。\n" +
        "林尽水源，便得一山，山有小口，仿佛若有光。便舍船，从口入。初极狭，才通人。复行数十步，豁然开朗。土地平旷，屋舍俨然，有良田美池桑竹之属。阡陌交通，鸡犬相闻。其中往来种作，男女衣着，悉如外人。黄发垂髫，并怡然自乐。\n" +
        "见渔人，乃大惊，问所从来。具答之。便要还家，设酒杀鸡作食。村中闻有此人，咸来问讯。自云先世避秦时乱，率妻子邑人来此绝境，不复出焉，遂与外人间隔。问今是何世，乃不知有汉，无论魏晋。此人一一为具言所闻，皆叹惋。余人各复延至其家，皆出酒食。停数日，辞去。此中人语云：“不足为外人道也。”\n" +
        "既出，得其船，便扶向路，处处志之。及郡下，诣太守，说如此。太守即遣人随其往，寻向所志，遂迷，不复得路。\n" +
        "南阳刘子骥，高尚士也，闻之，欣然规往。未果，寻病终，后遂无问津者。"