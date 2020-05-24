package cc.foxa.flip.ruleedit

import android.app.Dialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object RuleEditConfirmDialogFactory {
    fun create(fragment: RuleEditFragment,viewModel: RuleEditViewModel): Dialog {
        return MaterialAlertDialogBuilder(fragment.requireContext())
            .setTitle("保存规则？")
            .setPositiveButton("保存规则") {dialog, _ ->
                viewModel.save(true)
                dialog.dismiss()
            }
            .setNegativeButton("舍弃更改") {dialog, _ ->
                dialog.dismiss()
                fragment.findNavController().popBackStack()
            }
            .setNeutralButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}