package cc.foxa.flip.filteredit

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import cc.foxa.flip.databinding.DialogPriorityFilterEditBinding
import com.appyvet.materialrangebar.RangeBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PriorityFilterEditDialog(
    private val onConfirm: (formPriority: Int, toPriority: Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogPriorityFilterEditBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPriorityFilterEditBinding.inflate(layoutInflater, null, false)
        binding.range.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onTouchEnded(rangeBar: RangeBar?) {}

            override fun onRangeChangeListener(
                rangeBar: RangeBar?,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String?,
                rightPinValue: String?
            ) {
                binding.rangeText.text = "$leftPinValue~$rightPinValue"
            }

            override fun onTouchStarted(rangeBar: RangeBar?) {}

        })
        return MaterialAlertDialogBuilder(context)
            .setTitle("权值范围")
            .setView(binding.root)
            .setPositiveButton("确定") {dialog, which ->
                onConfirm(binding.range.leftPinValue.toInt(), binding.range.rightPinValue.toInt())
            }
            .setNegativeButton("取消") {dialog, which ->

            }
            .create()
    }
}