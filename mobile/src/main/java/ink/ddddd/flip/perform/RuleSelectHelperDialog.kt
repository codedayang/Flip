package ink.ddddd.flip.perform

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RuleSelectHelperDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(context)
            .setMessage("∩: 符合每个所选过滤规则的卡片\n∪: 符合任一所选过滤规则的卡片")
            .setPositiveButton("OK"){dialog, _ -> dialog.dismiss() }
            .create()
    }
}