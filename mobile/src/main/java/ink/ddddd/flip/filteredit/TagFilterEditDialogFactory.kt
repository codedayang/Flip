package ink.ddddd.flip.filteredit

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import ink.ddddd.flip.R
import ink.ddddd.flip.shared.data.model.Tag

object TagFilterEditDialogFactory {
    @SuppressLint("InflateParams")
    fun create(
        context: Context,
        tags: List<Tag>,
        onConfirm: (result: List<Tag>) -> Unit): Dialog {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_tag_filter_edit, null, false) as View

        val chipGroup = dialogView.findViewById<ChipGroup>(R.id.tags)
        tags.forEach {
            val chip = LayoutInflater.from(context)
                .inflate(R.layout.widget_filter_chip, null,false) as Chip
            chip.text = it.name
            chip.tag = it
            chipGroup.addView(chip)
        }
        return AlertDialog.Builder(context)
            .setTitle("包含Tag")
            .setPositiveButton("保存") { dialog, _ ->
                val tagList = mutableListOf<Tag>()
                chipGroup.forEach {
                    with (it as Chip) {
                        if (isChecked) {
                            tagList.add(it.tag as Tag)
                        }
                    }
                }
                if (tagList.isNotEmpty()) {
                    onConfirm(tagList)
                }
                dialog.dismiss()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setView(dialogView)
            .create()
    }
}