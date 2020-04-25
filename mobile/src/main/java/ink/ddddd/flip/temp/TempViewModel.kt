package ink.ddddd.flip.temp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TempViewModel @Inject constructor(
    private val updateCard: UpdateCard,
    private val updateTag: UpdateTag
) : ViewModel() {

    val updateCardResult = MutableLiveData<Result<Unit>>()

    val snackbar = MutableLiveData<Event<String>>()


    fun insertTag(tag: Tag) {
        updateTag(viewModelScope, tag)
    }
    fun populate(card: Card) {
        updateCard(viewModelScope, card, updateCardResult)
    }

    fun inflate(tag: Tag, card: Card) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val j1 = async { updateTag.executeNow(tag)}
                j1.await()
                val j2 = async { updateCard.executeNow(card) }
                j2.await()
                snackbar.postValue(Event("OK"))
            }
        }
    }
}