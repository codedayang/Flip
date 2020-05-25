package cc.foxa.flip.tagbrowse

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cc.foxa.flip.shared.data.model.Tag

@BindingAdapter(value = ["tags", "viewModel"], requireAll = true)
fun tags(chipGroup: ChipGroup, tags: List<Tag>?, viewModel: TagBrowseViewModel) {
    chipGroup.removeAllViews()
    tags?.forEach {
        val chip = Chip(chipGroup.context)
        chip.tag = it
        chip.text = it.name
        chip.setOnClickListener {_ ->
            viewModel.showTagEditDialog(it)
        }
        chipGroup.addView(chip)
    }
}