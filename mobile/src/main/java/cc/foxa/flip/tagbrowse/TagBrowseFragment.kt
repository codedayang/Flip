package cc.foxa.flip.tagbrowse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerDialogFragment
import cc.foxa.flip.R
import cc.foxa.flip.databinding.DialogTagEditBinding
import cc.foxa.flip.databinding.FragmentTagBrowseBinding
import cc.foxa.flip.shared.EventObserver
import cc.foxa.flip.shared.data.model.Tag
import javax.inject.Inject

class TagBrowseFragment : DaggerDialogFragment() {
    @Inject lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<TagBrowseViewModel> { factory }

    private lateinit var binding: FragmentTagBrowseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagBrowseBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        );
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSnackbar()
        setUpTagEditDialog()
        setUpAddAction()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpTagEditDialog() {
        viewModel.showTagEditDialog.observe(viewLifecycleOwner, EventObserver {
            val dialogViewBinding = DialogTagEditBinding.inflate(layoutInflater, null, false)
            dialogViewBinding.tag = it
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogViewBinding.root)
                .setTitle("编辑")
                .setPositiveButton("保存") { dialog, which ->
                    if (dialogViewBinding.tagNameInput.text.isNullOrBlank()) {
                        Snackbar.make(requireView(), "Tag不能为空！", Snackbar.LENGTH_SHORT).show()
                    } else {
                        viewModel.save(it)
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            dialogViewBinding.delete.setOnClickListener { view ->
                showDeleteConfirmDialog(it) {
                    dialog.dismiss()
                }
            }

            dialog.show()
        })


    }

    private fun showDeleteConfirmDialog(tag: Tag, onConfirm: () -> Unit) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("删除TAG: ${tag.name}？")
            .setPositiveButton("删除") {dialog, which ->
                viewModel.delete(tag)
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
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
                    viewModel.save(Tag(name = text.toString()))
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

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun setUpSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }
}