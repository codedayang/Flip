package ink.ddddd.flip.cardbrowse

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ink.ddddd.flip.R
import ink.ddddd.flip.databinding.FragmentCardBrowseBinding
import ink.ddddd.flip.filteredit.TagFilterEditDialogFactory
import ink.ddddd.flip.shared.EventObserver
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.filter.FILTER_NAME
import ink.ddddd.flip.shared.filter.Filter
import ink.ddddd.flip.shared.filter.TagFilter
import ink.ddddd.flip.temp.TempFragmentDirections
import ink.ddddd.flip.util.SpacesItemDecoration
import ink.ddddd.flip.util.anim.MEDIUM_EXPAND_DURATION
import ink.ddddd.flip.util.anim.fadeThrough
import kotlinx.android.synthetic.main.dialog_tag_filter_edit.*
import javax.inject.Inject
import kotlin.reflect.KClass

class CardBrowseFragment : DaggerFragment() {

    private lateinit var binding: FragmentCardBrowseBinding

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<CardBrowseViewModel> { factory }

    private var existRuleModeIsUnion = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBrowseBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar()
        setUpList()
        setUpNewCardButton()

        setUpDrawer()

        //Empty observe to active data
        viewModel.tags.observe(viewLifecycleOwner, Observer {  })

        viewModel.navigateToEditor.observe(viewLifecycleOwner, EventObserver {
            navigateToEditor(it)
        })
    }

    private fun setUpNewCardButton() {
        binding.newCard.setOnClickListener {
            navigateToEditor("")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.drawer.isDrawerOpen(GravityCompat.END)) {
                binding.drawer.closeDrawer(GravityCompat.END)
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpDrawer() {
        binding.cardBrowseDrawer.tempSetHint.setOnClickListener {
            val fadeThrough = fadeThrough()
            fadeThrough.duration = MEDIUM_EXPAND_DURATION
            TransitionManager.beginDelayedTransition(
                binding.cardBrowseDrawer.root as ViewGroup,
                fadeThrough
            )
            binding.cardBrowseDrawer.tempSet.visibility = View.VISIBLE
            binding.cardBrowseDrawer.tempSetHint.visibility = View.GONE
            binding.cardBrowseDrawer.existSet.visibility = View.GONE
            binding.cardBrowseDrawer.existSetHint.visibility = View.VISIBLE
        }
        binding.cardBrowseDrawer.existSetHint.setOnClickListener {
            val fadeThrough = fadeThrough()
            fadeThrough.duration = MEDIUM_EXPAND_DURATION
            TransitionManager.beginDelayedTransition(
                binding.cardBrowseDrawer.root as ViewGroup,
                fadeThrough
            )
            binding.cardBrowseDrawer.tempSet.visibility = View.GONE
            binding.cardBrowseDrawer.tempSetHint.visibility = View.VISIBLE
            binding.cardBrowseDrawer.existSet.visibility = View.VISIBLE
            binding.cardBrowseDrawer.existSetHint.visibility = View.GONE
        }

        setUpTempRuleSection()
        setUpExistRuleSection()
    }

    private fun setUpExistRuleSection() {
        binding.cardBrowseDrawer.existSetApply.setOnClickListener {
            val rules = mutableListOf<Rule>()
            binding.cardBrowseDrawer.chipGroup.forEach {
                if (it.id != binding.cardBrowseDrawer.clearRule.id && (it as Chip).isChecked) {
                    rules.add(it.tag as Rule)
                }
            }
            viewModel.applyRuleSet(RuleSet(isUnion = existRuleModeIsUnion, rules = rules))
            binding.drawer.closeDrawer(GravityCompat.END)
        }
        binding.cardBrowseDrawer.existSetModeToggle.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                binding.cardBrowseDrawer.existSetModeIntersection.id -> {
                    if (isChecked) existRuleModeIsUnion = false
                }
                binding.cardBrowseDrawer.existSetModeUnion.id -> {
                    if (isChecked) existRuleModeIsUnion = true
                }
            }
        }
        binding.cardBrowseDrawer.clearRule.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cardBrowseDrawer.chipGroup.forEach {
                    if (it.id != R.id.clear_rule) {
                        (it as Chip).isChecked = false
                    }

                }
            }
        }
        viewModel.rules.observe(viewLifecycleOwner, Observer {
            bindExistRuleChips(it, binding.cardBrowseDrawer.chipGroup)
        })

    }

    private fun setUpTempRuleSection() {
        binding.cardBrowseDrawer.tempSetAddFilter.apply {
            setItems(FILTER_NAME.values.toList())
            setOnItemSelectedListener { view, position, id, item ->
                showTempRuleAddFilterDialog(FILTER_NAME.keys.toList()[position])
            }
        }
        binding.cardBrowseDrawer.tempSetFilters.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TempFilterListAdapter(viewModel)
            addItemDecoration(SpacesItemDecoration(12))
        }
        binding.cardBrowseDrawer.tempSetClear.setOnClickListener {
            val size = viewModel.tempRuleFilters.size
            viewModel.tempRuleFilters.clear()
            (binding.cardBrowseDrawer.tempSetFilters.adapter as TempFilterListAdapter).apply {
                submitList(viewModel.tempRuleFilters)
                notifyItemRangeRemoved(0, size)
            }
        }
        binding.cardBrowseDrawer.tempSetApply.setOnClickListener {
            val ruleSet = RuleSet(rules = listOf(Rule(filters = viewModel.tempRuleFilters)))
            viewModel.applyRuleSet(ruleSet)
            Snackbar.make(binding.root, "已应用", Snackbar.LENGTH_SHORT).show()
            binding.drawer.closeDrawer(GravityCompat.END)
        }
        binding.cardBrowseDrawer.tempSetCancel.setOnClickListener {
            binding.drawer.closeDrawer(GravityCompat.END)
        }

        viewModel.refreshTempRuleFilter.observe(viewLifecycleOwner, EventObserver {
            (binding.cardBrowseDrawer.tempSetFilters.adapter as TempFilterListAdapter).apply {
                submitList(viewModel.tempRuleFilters)
                notifyDataSetChanged()
            }
        })

    }

    private fun showTempRuleAddFilterDialog(klass: KClass<out Filter>) {
        when (klass) {
            TagFilter::class -> {
                TagFilterEditDialogFactory.create(requireContext(), viewModel.tags.value!!) {
                    viewModel.tempRuleFilters.add(TagFilter(tags = it))
                    (binding.cardBrowseDrawer.tempSetFilters.adapter as TempFilterListAdapter).apply {
                        submitList(viewModel.tempRuleFilters)
                        notifyItemInserted(viewModel.tempRuleFilters.size-1)
                    }
                }.show()
            }
        }
    }

    private fun setUpList() {
        binding.cardList.layoutManager = GridLayoutManager(
            context, 2, RecyclerView.VERTICAL, false
        )
    }


    private fun setUpToolBar() {
        binding.toolbar.apply {
            inflateMenu(R.menu.menu_card_browse)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.filter_list -> {
                        binding.drawer.openDrawer(GravityCompat.END)
                    }
                }
                true
            }
        }

    }

    private fun bindExistRuleChips(rules: List<Rule>?, chipGroup: ChipGroup) {
        val count = chipGroup.childCount
        for (i in 0 until count) {
            if (chipGroup.getChildAt(i) != null) {
                if (chipGroup.getChildAt(i).id != R.id.clear_rule) {
                    chipGroup.removeView(chipGroup.getChildAt(i))
                }
            }
        }

        val context = chipGroup.context
        rules?.forEach {
            val chip = LayoutInflater.from(context).inflate(R.layout.widget_rule_chip, null, false) as Chip
            chip.apply {
                text = it.name
                tag = it
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        chipGroup.findViewById<Chip>(R.id.clear_rule).isChecked = false
                    }
                }
            }
            chipGroup.addView(chip)
        }

    }

    fun navigateToEditor(cardId: String) {
        val action = CardBrowseFragmentDirections.actionCardBrowseFragmentToCardEditFragment(cardId)
        findNavController().navigate(action)
    }
}