package cc.foxa.flip.ruleedit

import androidx.lifecycle.*
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.Result
import cc.foxa.flip.shared.data.NoSuchRuleException
import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.domain.rule.DeleteRule
import cc.foxa.flip.shared.domain.rule.GetRuleById
import cc.foxa.flip.shared.domain.rule.GetRules
import cc.foxa.flip.shared.domain.rule.UpdateRule
import cc.foxa.flip.shared.domain.tag.GetTags
import javax.inject.Inject

class RuleEditViewModel @Inject constructor(
    private val getRuleById: GetRuleById,
    private val getTags: GetTags,
    private val updateRule: UpdateRule,
    private val deleteRule: DeleteRule
) : ViewModel() {
    private val getRuleResult = MutableLiveData<Result<Rule>>()

    val getRuleState = MutableLiveData<Int>(STATE_LOADING)

    val rule =getRuleResult.map {
        when (it) {
            is Result.Success -> {
                getRuleState.value = STATE_SUCCESS
                it.data
            }
            is Result.Error -> {
                when (it.exception) {
                    is NoSuchRuleException -> {
                        Rule(filters = emptyList())
                    }
                    else -> {
                        getRuleState.value = STATE_FAILED
                        null
                    }
                }
            }
        }
    } as MediatorLiveData

    private val getTagsResult = MutableLiveData<Result<List<Tag>>>()

    val getTagsState = MutableLiveData<Int>(STATE_LOADING)

    val tags = getTagsResult.map {
        when (it) {
            is Result.Success -> {
                getTagsState.value = STATE_SUCCESS
                it.data
            }
            else -> {
                getTagsState.value = STATE_FAILED
                null
            }
        }
    }

    val refreshFilterList = MutableLiveData<Event<Unit>>()

    val close = MutableLiveData<Event<Unit>>()

    val snackbar = MutableLiveData<Event<String>>()

    var changed = false;

    init {
        getTags(viewModelScope, Unit, getTagsResult)
    }
    fun loadRule(id: String) {
        getRuleState.value = STATE_LOADING
        getRuleById(viewModelScope, id, getRuleResult)
    }

    fun save(close: Boolean) {
        if (validateRule()) {
            updateRule(viewModelScope, rule.value!!)
            snackbar.value = Event("保存成功")
            if (close) this.close.value = Event(Unit)
        }
    }

    fun delete() {
        deleteRule(viewModelScope, rule.value!!)
        snackbar.value = Event("已删除")
        close.value = Event(Unit)
    }

    private fun validateRule(): Boolean {
        if (rule.value!!.name.isEmpty()) {
            snackbar.value = Event("保存失败：名称不能为空")
            return false
        }
        if (rule.value!!.filters.isEmpty()) {
            snackbar.value = Event("保存失败：请添加过滤器")
            return false
        }
        return true
    }

    fun next() {
        save(false)
        if (validateRule()) {
            rule.value = Rule()
            refreshFilterList.value = Event(Unit)
            changed = false
        }
    }

    companion object {
        const val STATE_LOADING = 0x01
        const val STATE_FAILED = 0x02
        const val STATE_SUCCESS = 0x03
    }
}