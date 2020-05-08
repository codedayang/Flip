package ink.ddddd.flip.temp

import androidx.lifecycle.*
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.GetCardById
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.rule.UpdateRule
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import ink.ddddd.flip.shared.filter.TagFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class TempViewModel @Inject constructor(
    private val updateCard: UpdateCard,
    private val updateTag: UpdateTag,
    private val getCardById: GetCardById,
    private val updateRule: UpdateRule
) : ViewModel() {

    val updateCardResult = MutableLiveData<Result<Unit>>()

    val snackbar = MutableLiveData<Event<String>>()

    val getCardResult = MutableLiveData<Result<Card>>()

    val card = getCardResult.map {
        when (it) {
            is Result.Success -> {

            }
            is Result.Error -> {
                Timber.d(it.exception::class.qualifiedName)
                snackbar.value = Event(it.exception::class.qualifiedName!!)
            }
        }
    }


    fun insertTag(tag: Tag) {
        updateTag(viewModelScope, tag)
    }
    fun populate(card: Card) {
        updateCard(viewModelScope, card, updateCardResult)
    }

    fun inflate(tag: Tag, card: Card) {
        val rule = Rule(name = "Test", filters = listOf(TagFilter(tags = listOf(tag))))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val j1 = async { updateTag.executeNow(tag)}
                j1.await()
                val j2 = async { updateCard.executeNow(card) }
                j2.await()
                val j3 = async { updateRule.executeNow(rule) }
                j3.await()
                snackbar.postValue(Event("OK"))
            }
        }
    }

    fun queryInvalidCard() {
        getCardById(viewModelScope, "Foo", getCardResult)
    }
}