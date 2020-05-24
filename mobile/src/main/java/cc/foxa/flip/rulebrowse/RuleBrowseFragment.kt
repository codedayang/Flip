package cc.foxa.flip.rulebrowse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import cc.foxa.flip.databinding.FragmentRuleBrowseBinding
import cc.foxa.flip.shared.EventObserver
import cc.foxa.flip.util.SpacesItemDecoration
import javax.inject.Inject

class RuleBrowseFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<RuleBrowseViewModel> { factory }

    private lateinit var binding: FragmentRuleBrowseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRuleBrowseBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        setUpToolbar()

        viewModel.navigateToEditor.observe(viewLifecycleOwner, EventObserver {
            val action = RuleBrowseFragmentDirections.actionRuleBrowseFragmentToRuleEditFragment(it)
            findNavController().navigate(action)
        })

        binding.newRule.setOnClickListener {
            val action = RuleBrowseFragmentDirections.actionRuleBrowseFragmentToRuleEditFragment("")
            findNavController().navigate(action)
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpList() {
        binding.ruleList.layoutManager = LinearLayoutManager(context)
        binding.ruleList.addItemDecoration(SpacesItemDecoration(8))
        binding.ruleList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}