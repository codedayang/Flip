package ink.ddddd.flip.perform

import android.content.Intent
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
import ink.ddddd.flip.onboarding.OnBoardingActivity
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

    private val ruleSelectHelperDialog by lazy { RuleSelectHelperDialog() }

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
        setUpLeftMenu()
        setUpAddRuleAction()
        setUpRuleSelectHelper()

        return binding.root
    }

    private fun setUpRuleSelectHelper() {
        viewModel.showRuleSelectHelper.observe(viewLifecycleOwner, EventObserver {
            ruleSelectHelperDialog.show(parentFragmentManager, null)
        })
        binding.help.setOnClickListener {
            ruleSelectHelperDialog.show(parentFragmentManager, null)
        }
    }

    private fun setUpLeftMenu() {
        binding.menu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.to_card_browser -> {
                    val action = PerformFragmentDirections.actionPerformFragmentToCardBrowseFragment()
                    findNavController().navigate(action)
                }
                R.id.to_rule_browser -> {
                    val action = PerformFragmentDirections.actionPerformFragmentToRuleBrowseFragment()
                    findNavController().navigate(action)
                }
                R.id.to_tag_edit -> {
                    val action = PerformFragmentDirections.actionPerformFragmentToTagBrowseFragment()
                    findNavController().navigate(action)
                }
                R.id.to_settings -> {
                    Snackbar.make(binding.root, "在路上了", Snackbar.LENGTH_SHORT).show()
                }
                R.id.to_manual_guide -> {
                    startActivity(Intent(context, OnBoardingActivity::class.java))
                }
            }
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: use default activity finish animation
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            ruleSelectHelperDialog.dismiss()
//            when {
//                binding.menuDrawer.isDrawerOpen(GravityCompat.START) -> {
//                    binding.menuDrawer.closeDrawer(GravityCompat.START)
//                }
//                binding.ruleSelectDrawer.isDrawerOpen(Gravity.TOP) -> {
//                    binding.ruleSelectDrawer.closeDrawer(Gravity.TOP)
//                }
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRules()
        viewModel.loadCard()
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


    }

    private fun setUpAddRuleAction() {
        binding.add.setOnClickListener {
            val action = PerformFragmentDirections.actionPerformFragmentToRuleEditFragment("")
            findNavController().navigate(action)
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
                    if (binding.ruleSelectDrawer.isDrawerOpen(Gravity.TOP)) {
                        closeRuleSelect()
                    } else {
                        openRuleSelect()
                    }
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
        viewModel.shouldShowRuleSelectHelper()
    }

    fun closeRuleSelect() {
        binding.ruleSelectDrawer.close()

    }
}