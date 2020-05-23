package ink.ddddd.flip.cardedit

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CardEditHelperDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(context)
            .setMessage("支持Html语法\nLatex公式请使用“$$ $$”包裹\n换行请使用<br />👈这个问题会在接下来的版本解决\nweb端编辑器在路上了")
            .setPositiveButton("OK"){ dialog, _ ->  dialog.dismiss()}
            .create()
    }
}