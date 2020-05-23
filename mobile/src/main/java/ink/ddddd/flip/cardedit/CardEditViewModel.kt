package ink.ddddd.flip.cardedit

import androidx.lifecycle.*
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.NoSuchCardException
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.DeleteCard
import ink.ddddd.flip.shared.domain.card.GetCardById
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.pref.cardedit.CardEditHelperIsShown
import ink.ddddd.flip.shared.domain.pref.cardedit.CardEditHelperSetShown
import timber.log.Timber
import javax.inject.Inject

class CardEditViewModel @Inject constructor(
    private val getCardById: GetCardById,
    private val updateCard: UpdateCard,
    private val deleteCard: DeleteCard,
    private val cardEditHelperIsShown: CardEditHelperIsShown,
    private val cardEditHelperSetShown: CardEditHelperSetShown
) : ViewModel() {


    val getCardResult = MutableLiveData<Result<Card>>()

    val editState = MutableLiveData<Int>()

    val finish = MutableLiveData<Event<Unit>>()


    val card = MediatorLiveData<Card>()

    var originCard = Card()

    var changed = false


    val snackbar = MutableLiveData<Event<String>>()

    var previewState = PREVIEW_STATE_FRONT

    val showHelperDialog = MediatorLiveData<Event<Unit>>()

    init {
        showHelperDialog.addSource(cardEditHelperIsShown(viewModelScope, Unit)) {
            if (!(it as? Result.Success)?.data!!) {
                showHelperDialog.value = Event(Unit)
                cardEditHelperSetShown(viewModelScope, true)
            }
        }
        card.addSource(getCardResult) {
            when (it) {
                is Result.Success -> {
                    card.value = it.data
                    originCard = it.data
                    editState.value = EDIT_STATE_SUCCESS
                }
                is Result.Error -> {
                    if (it.exception is NoSuchCardException) {
                        card.value = Card()
                        originCard = card.value!!
                        editState.value = EDIT_STATE_SUCCESS
                    }
                }
                else -> {
                    editState.value = EDIT_STATE_FAILED
                }
            }
        }
    }

    fun loadCard(cardId: String) {
        editState.value = EDIT_STATE_LOADING
        getCardById(viewModelScope, cardId, getCardResult)
    }

    fun delete() {
        deleteCard(viewModelScope, card.value!!)
        snackbar.value = Event("已删除")
        finish.value = Event(Unit)
    }

    fun changePriority(delta: Int) {
        val t = card.value!!
        val priority = t.priority + delta
        if (priority < 1) {
            snackbar.value = Event("优先级不可小于1")
            return
        }
        t.priority = priority
        card.value = t
        changed = true
    }

    fun nextCard() {
        if (!checkCard()) return
        save(false)
        card.value = Card()
        changed = false
    }

    fun save(close: Boolean) {
        if (checkCard()) {
            val t = card.value!!
            updateCard(viewModelScope, t)
            snackbar.value = Event("已保存")
            if (close) finish.value = Event(Unit)
        }
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