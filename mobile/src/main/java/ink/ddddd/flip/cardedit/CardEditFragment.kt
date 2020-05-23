package ink.ddddd.flip.cardedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.forEach
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ink.ddddd.flip.databinding.FragmentCardEditBinding
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.EventObserver
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.tagedit.TagEditFragment
import ink.ddddd.flip.widget.DoubleSideCardView
import ink.ddddd.flip.widget.ViewPagerAdapter
import javax.inject.Inject

class CardEditFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<CardEditViewModel> { factory }

    private lateinit var binding: FragmentCardEditBinding

    private val args: CardEditFragmentArgs by navArgs<CardEditFragmentArgs>()

    private var firstLoadFront = true
    private var firstLoadBack = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val dialog = CardEditConfirmDialogFactory.create(this@CardEditFragment, viewModel)
            if (viewModel.changed) {
                dialog.show()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardEditBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setUpViewPager()
        setUpDeleteAction()
        setUpActionBar()
        setUpEvents()
        setUpSnackBar()
        setUpTagEditAction()

        viewModel.finish.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })


        binding.cardPreview.card.setState(DoubleSideCardView.STATE_FRONT, false)
        viewModel.previewState = CardEditViewModel.PREVIEW_STATE_FRONT
        binding.cardPreview.filp.setOnClickListener {
            if (viewModel.previewState == CardEditViewModel.PREVIEW_STATE_FRONT) {
                binding.cardPreview.card.setState(DoubleSideCardView.STATE_BACK, true)
                viewModel.previewState = CardEditViewModel.PREVIEW_STATE_BACK
            } else {
                binding.cardPreview.card.setState(DoubleSideCardView.STATE_FRONT, true)
                viewModel.previewState = CardEditViewModel.PREVIEW_STATE_FRONT
            }

        }


        return binding.root
    }

    private fun setUpTagEditAction() {
        binding.cardEdit.editTag.setOnClickListener {
            if (viewModel.card.value == null) return@setOnClickListener
            val frag = TagEditFragment(viewModel.card.value!!) {
                val t = viewModel.card.value!!
                t.tags = it;
                viewModel.card.value = t
            }
            frag.show(childFragmentManager, null)
        }
    }

    private fun setUpSnackBar() {
        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setUpEvents() {
        binding.modeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == binding.combo.id) {
                if (isChecked) {
                    binding.next.visibility = View.VISIBLE
                    binding.save.visibility = View.GONE
                } else {
                    binding.next.visibility = View.GONE
                    binding.save.visibility = View.VISIBLE
                }
            }
        }
        binding.cardEdit.importContent.setOnClickListener {
            viewModel.snackbar.value = Event("施工中")
        }

        binding.save.setOnClickListener {
            updateCardTag()
            viewModel.save(true)
        }

        binding.next.setOnClickListener {
            updateCardTag()
            viewModel.nextCard()
        }


        binding.cardEdit.editFrontInput.addTextChangedListener {
            val text = it.toString()
            if (firstLoadFront) {
                firstLoadFront = false
            } else {
                viewModel.changed = true
            }
            binding.cardPreview.frontCardFront.setText(text)
            binding.cardPreview.backCardFront.setText(text)

        }

        binding.cardEdit.editBackInput.addTextChangedListener {
            val text = it.toString()
            if (firstLoadBack) {
                firstLoadBack = false
            } else {
                viewModel.changed = true
            }
            binding.cardPreview.backCardBack.setText(text)

        }

        binding.help.setOnClickListener {
            CardEditHelperDialog().show(parentFragmentManager, null)
        }

        viewModel.showHelperDialog.observe(viewLifecycleOwner, EventObserver {
            CardEditHelperDialog().show(parentFragmentManager, null)
        })


    }

    private fun updateCardTag() {
        val tags = mutableListOf<Tag>()
        binding.cardEdit.editTags.forEach {
            if (it.id != binding.cardEdit.editTag.id) {
                tags.add(it.tag as Tag)
            }
        }
        val t = viewModel.card.value!!
        t.tags = tags
        viewModel.card.value = t
    }

    private fun setUpActionBar() {
        binding.toolbar.setNavigationOnClickListener {
            val dialog = CardEditConfirmDialogFactory.create(this@CardEditFragment, viewModel)
            if (viewModel.changed) {
                dialog.show()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpViewPager() {
        binding.pager.adapter = ViewPagerAdapter(
            listOf(
                binding.cardEdit.root,
                binding.cardPreview.root
            ),
            listOf(
                "编辑",
                "预览"
            )
        )


        binding.tabs.setupWithViewPager(binding.pager)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCard(args.cardId)
    }

    private fun setUpDeleteAction() {
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle("删除卡片")
            .setPositiveButton("删除") { _, _ ->
                viewModel.delete()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.dismiss()
            }
        binding.cardEdit.delete.setOnClickListener {
            dialog.show()
        }

    }
}