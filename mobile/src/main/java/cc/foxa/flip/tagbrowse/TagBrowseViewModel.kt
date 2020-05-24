package cc.foxa.flip.tagbrowse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.Result
import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.domain.tag.DeleteTag
import cc.foxa.flip.shared.domain.tag.GetTags
import cc.foxa.flip.shared.domain.tag.UpdateTag
import javax.inject.Inject

class TagBrowseViewModel @Inject constructor(
    private val getTags: GetTags,
    private val deleteTag: DeleteTag,
    private val updateTag: UpdateTag
) : ViewModel() {
    val getTagsState = MutableLiveData<Int>(STATE_LOADING)

    val getTagsResult = MutableLiveData<Result<List<Tag>>>()

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

    val snackbar = MutableLiveData<Event<String>>()

    val showTagEditDialog = MutableLiveData<Event<Tag>>()

    init {
        getTags(viewModelScope, Unit, getTagsResult)
    }

    fun showTagEditDialog(tag: Tag) {
        showTagEditDialog.value = Event(tag)
    }

    fun save(tag: Tag) {
        updateTag(viewModelScope, tag)
        snackbar.value = Event("已保存")
        refresh()
    }

    fun delete(tag: Tag) {
        deleteTag(viewModelScope, tag)
        snackbar.value = Event("已删除")
        refresh()
    }

    fun refresh() {
        getTagsState.value = STATE_LOADING
        getTags(viewModelScope, Unit, getTagsResult)
    }

    companion object {
        const val STATE_LOADING = 0x01
        const val STATE_FAILED = 0x02
        const val STATE_SUCCESS = 0x03
    }
}