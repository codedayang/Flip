package cc.foxa.flip.filteredit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cc.foxa.flip.databinding.DialogKeywordFilterEditBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class KeyWordFilterEditDialog(private val onConfirm: (String) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogKeywordFilterEditBinding

    private lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogKeywordFilterEditBinding.inflate(layoutInflater, null, false)
        binding.keyword.addTextChangedListener {
            binding.keywordLayout.error = null
        }
        dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .setTitle("包含关键字")
            .setPositiveButton("确定", null)
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .apply {
                setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val text = binding.keyword.text.toString()
                        if (text.isNotEmpty() && text.isNotBlank()) {
                            onConfirm(text)
                            dismiss()
                        } else {
                            binding.keywordLayout.error = "关键字不能为空"
                        }
                    }
                }
            }
        return dialog

    }
}