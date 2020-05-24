package cc.foxa.flip.filteredit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import cc.foxa.flip.databinding.DialogWithinDaysFilterEditBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class WithinDaysFilterEditDialog(
    private val onConfirm: (Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogWithinDaysFilterEditBinding

    private lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogWithinDaysFilterEditBinding.inflate(layoutInflater, null, false)
        binding.day.addTextChangedListener {
            binding.keywordLayout.error = null
        }
        dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .setTitle("X天之内添加的卡片")
            .setPositiveButton("确定", null)
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .apply {
                setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val days = binding.day.text.toString()
                        if (days.isNotEmpty() && days.isNotBlank()) {
                            onConfirm(days.toInt())
                            dismiss()
                        } else {
                            binding.keywordLayout.error = "不能为空"
                        }
                    }
                }
            }
        return dialog
    }
}