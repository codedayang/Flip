package ink.ddddd.flip.cardedit

import android.app.Dialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object CardEditConfirmDialogFactory {
    fun create(fragment: CardEditFragment,viewModel: CardEditViewModel): Dialog {
        return MaterialAlertDialogBuilder(fragment.requireContext())
            .setTitle("保存卡片？")
            .setPositiveButton("保存卡片") {dialog, _ ->
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