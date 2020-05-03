package ink.ddddd.flip.perform

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ink.ddddd.flip.R
import ink.ddddd.flip.databinding.FragmentPerformBinding
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.EventObserver
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.RuleSet
import javax.inject.Inject

class PerformFragment : DaggerFragment() {

    private lateinit var binding: FragmentPerformBinding

    @Inject lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<PerformViewModel> { factory }

    private var ruleSetModeIsUnion = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerformBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpViewModelEvent()
        setUpToolbar()
        setUpRuleChipGroup()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when {
                binding.menuDrawer.isDrawerOpen(GravityCompat.START) -> {
                    binding.menuDrawer.closeDrawer(GravityCompat.START)
                }
                binding.ruleSelectDrawer.isDrawerOpen(Gravity.TOP) -> {
                    binding.ruleSelectDrawer.closeDrawer(Gravity.TOP)
                }
                else -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setUpRuleChipGroup() {
        binding.clearRule.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipGroup.forEach {
                    if (it.id != binding.clearRule.id) {
                        (it as Chip).isChecked = false
                    }

                }
            }
        }
        binding.apply.setOnClickListener {
            val rules = mutableListOf<Rule>()
            binding.chipGroup.forEach {
                if (it.id != binding.clearRule.id && (it as Chip).isChecked) {
                    rules.add(it.tag as Rule)
                }
            }
            val ruleSet = RuleSet(isUnion = ruleSetModeIsUnion, rules = rules)
            viewModel.applyRuleSet(ruleSet)
            binding.ruleSelectDrawer.close()
        }
        binding.modeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == binding.union.id) ruleSetModeIsUnion = isChecked
        }
        binding.cancel.setOnClickListener {
            binding.ruleSelectDrawer.close()
        }

        binding.add.setOnClickListener {
            viewModel.snackbar.value = Event("TODO")
        }

    }

    private fun setUpViewModelEvent() {
        viewModel.executePendingBinding.observe(viewLifecycleOwner, EventObserver {
            binding.executePendingBindings()
        })

        viewModel.resetCardScroller.observe(viewLifecycleOwner, EventObserver {

            binding.front.scrollTo(0, 0)
            binding.back.scrollTo(0,0)
        })

        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setUpToolbar() {
        binding.toolbar.inflateMenu(R.menu.menu_perform)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.expand -> {
                    openRuleSelect()
                }
            }
            true
        }

        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_menu_24dp, context?.theme)
        binding.toolbar.setNavigationOnClickListener {
            binding.menuDrawer.open()
        }

    }

    fun openRuleSelect() {
        binding.ruleSelectDrawer.open()
    }

    fun closeRuleSelect() {
        binding.ruleSelectDrawer.close()

    }
}