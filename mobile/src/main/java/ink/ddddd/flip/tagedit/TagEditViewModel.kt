package ink.ddddd.flip.tagedit

import androidx.lifecycle.*
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.GetCardById
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.tag.GetTags
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import javax.inject.Inject

class TagEditViewModel @Inject constructor(
    private val getTags: GetTags,
    private val updateTag: UpdateTag
) : ViewModel() {

    var curCard: Card = Card()


    val getTagsResult = MutableLiveData<Result<List<Tag>>>()

    val tags: LiveData<List<TagWrapper>> = Transformations.map(getTagsResult) {
        val res: List<TagWrapper>?
        res = when (it) {
            is Result.Success -> {
                state.value = STATE_TAG_EDIT_SUCCESS
                it.data.map {tag ->
                    if (curCard.tags.contains(tag)) {
                        TagWrapper(tag, true)
                    } else {
                        TagWrapper(tag, false)
                    }
                }
            }
            else -> {
                state.value = STATE_TAG_EDIT_FAILED
                null
            }
        }
        res!!
    }

    val state = MutableLiveData<Int>(STATE_TAG_EDIT_LOADING)

    fun loadTags(card: Card) {
        state.value = STATE_TAG_EDIT_LOADING
        curCard = card
        getTags(viewModelScope, Unit, getTagsResult)
    }

    fun addTag(name: String) {
        updateTag(viewModelScope, Tag(name = name))
        loadTags(curCard)
    }

    companion object {
        const val STATE_TAG_EDIT_LOADING = 0x01
        const val STATE_TAG_EDIT_FAILED = 0x02
        const val STATE_TAG_EDIT_SUCCESS = 0x02
    }

}