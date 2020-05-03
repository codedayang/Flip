package ink.ddddd.flip.tagedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerDialogFragment
import ink.ddddd.flip.R
import ink.ddddd.flip.databinding.FragmentTagEditBinding
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import javax.inject.Inject

class TagEditFragment(
    private val card: Card,
    private val onConfirm: (tags: List<Tag>) -> Unit
) : DaggerDialogFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val tagEditVm by viewModels<TagEditViewModel> { factory }

    private lateinit var binding: FragmentTagEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        );
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagEditBinding.inflate(inflater, container, false)
        binding.tagEditVm = tagEditVm
        binding.lifecycleOwner = viewLifecycleOwner
        tagEditVm.loadTags(card)

        setUpAddAction()
        setUpConfirmAction()
        setUpCancelAction()
        return binding.root
    }

    private fun setUpCancelAction() {
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpConfirmAction() {
        binding.confrim.setOnClickListener {
            val tags = mutableListOf<Tag>()
            binding.tags.forEach {
                val chip = it as Chip
                if (chip.isChecked) {
                    tags.add(chip.tag as Tag)
                }
            }
            onConfirm(tags)
            dismiss()
        }

    }

    private fun setUpAddAction() {
        val dialogView = LayoutInflater
            .from(context)
            .inflate(R.layout.dialog_add_tag, null, false) as View
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("新建Tag")
            .setPositiveButton("保存") { dialog, _ ->
                val text = dialogView.findViewById<TextInputEditText>(R.id.tag_name).text
                if (text.isNullOrBlank()) {
                    Snackbar.make(requireView(), "Tag不能为空！", Snackbar.LENGTH_SHORT).show()
                } else {
                    tagEditVm.addTag(text.toString())
                    dialog.dismiss()
                }
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
            .setView(dialogView)
            .create()

        binding.add.setOnClickListener {
            dialogView.findViewById<TextInputEditText>(R.id.tag_name).setText("")
            dialog.show()
        }
    }
}