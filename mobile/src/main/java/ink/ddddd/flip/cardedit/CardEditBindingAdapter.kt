package ink.ddddd.flip.cardedit

import android.view.LayoutInflater
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ink.ddddd.flip.R
import ink.ddddd.flip.shared.data.model.Tag

@BindingAdapter("editTags")
fun editTags(chipGroup: ChipGroup, tags: List<Tag>?) {
    val childCount = chipGroup.childCount
    for (index in 0 until childCount) {
        val child = chipGroup.getChildAt(index) ?: continue
        if (child.id != R.id.edit_tag) {
            chipGroup.removeView(child)
        }
    }
    chipGroup.forEach {
        if (it.id != R.id.edit_tag) {
            chipGroup.removeView(it)
        }
    }
    tags?.forEach { tag ->
        val chip = LayoutInflater
            .from(chipGroup.context)
            .inflate(R.layout.widget_edit_tag_chip, null, false) as Chip
        chip.tag = tag
        chip.text = tag.name
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(it)
        }
        chipGroup.addView(chip)

    }

}

