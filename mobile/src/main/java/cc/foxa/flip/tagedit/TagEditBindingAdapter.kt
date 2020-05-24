package cc.foxa.flip.tagedit

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cc.foxa.flip.R

@BindingAdapter("tags")
fun tags(chipGroup: ChipGroup, tags: List<TagWrapper>?) {
    chipGroup.removeAllViews()
    tags?.forEach {
        val chip = LayoutInflater
            .from(chipGroup.context)
            .inflate(R.layout.widget_tag_edit_tag_chip, null, false) as Chip
        chip.isChecked = it.isChecked
        chip.tag = it.tag
        chip.text = it.tag.name
        chipGroup.addView(chip)
    }

}