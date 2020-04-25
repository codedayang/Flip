package ink.ddddd.flip.cardedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.DeleteCard
import ink.ddddd.flip.shared.domain.card.GetCardById
import ink.ddddd.flip.shared.domain.card.UpdateCard
import timber.log.Timber
import javax.inject.Inject

class CardEditViewModel @Inject constructor(
    private val getCardById: GetCardById,
    private val updateCard: UpdateCard,
    private val deleteCard: DeleteCard
) : ViewModel() {


    val getCardResult = MutableLiveData<Result<Card>>()

    val editState = MutableLiveData<Int>()

    val finish = MutableLiveData<Event<Unit>>()

    val card = Transformations.map(getCardResult) {
        when (it) {
            is Result.Success -> {
                editState.value = EDIT_STATE_SUCCESS
                if (isFirstLoad && it.data.front.isNotEmpty()) {
                    originFront = it.data.front
                    originBack = it.data.back
                    originPriority = it.data.priority
                    originTags = it.data.tags
                    isFirstLoad = false
                }
                it.data
            }
            else -> {
                editState.value = EDIT_STATE_FAILED
                null
            }
        }
    }
    private var originFront = ""
    private var originBack = ""
    private var originPriority = 0
    private var originTags = listOf<Tag>()

    var changed = false
    var isFirstLoad = true


    val snackbar = MutableLiveData<Event<String>>()

    var previewState = PREVIEW_STATE_FRONT

    fun loadCard(cardId: String) {
        editState.value = EDIT_STATE_LOADING
        getCardById(viewModelScope, cardId, getCardResult)
    }

    fun delete() {
        if (card.value != null) {
            deleteCard(viewModelScope, card.value!!)
            snackbar.value = Event("已删除")
            finish.value = Event(Unit)
        }
    }

    fun changePriority(delta: Int) {
        val t = card.value!!
        val priority = t.priority + delta
        if (priority < 1) {
            snackbar.value = Event("优先级不可小于1")
            return
        }
        t.priority = priority
        getCardResult.value = Result.Success(t)
        changed = true
    }

    fun nextCard() {
        if (!checkCard()) return
        editState.value = EDIT_STATE_LOADING
        save(false)
        getCardResult.value = Result.Success(Card())
        isFirstLoad = true
    }

    fun discardChanges() {
        discardTagChange()
        discardPriorityChange()
    }

    private fun discardPriorityChange() {
        val t = card.value!!
        t.priority = originPriority
        getCardResult.value = Result.Success(t)
        updateCard(viewModelScope, t)
    }

    fun discardTagChange() {
        val t = card.value!!
        t.tags = originTags
        getCardResult.value = Result.Success(t)
        updateCard(viewModelScope, t)
    }

    fun save(close: Boolean) {
        if (checkCard()) {
            val t = card.value!!
            updateCard(viewModelScope, t)
            snackbar.value = Event("已保存")
            if (close) finish.value = Event(Unit)
        }
    }

    fun isChanged(): Boolean {

        Timber.d(originFront)

        if (card.value?.front!!.contentEquals(originFront)
            && card.value?.back!!.contentEquals(originBack)
            && !changed
        ) return false
        return true
    }

    private fun checkCard(): Boolean {
        if (card.value!!.front.isBlank()) {
            snackbar.value = Event("卡片正面不能为空")
            return false
        }
        if (card.value!!.back.isEmpty()) {
            snackbar.value = Event("卡片背面不能为空")
            return false
        }
        return true
    }

    companion object {
        const val EDIT_STATE_LOADING = 0x01
        const val EDIT_STATE_SUCCESS = 0x02
        const val EDIT_STATE_FAILED = 0x03

        const val PREVIEW_STATE_FRONT = 1
        const val PREVIEW_STATE_BACK = -1

    }

}