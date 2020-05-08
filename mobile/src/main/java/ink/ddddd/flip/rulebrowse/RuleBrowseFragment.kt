package ink.ddddd.flip.rulebrowse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import ink.ddddd.flip.databinding.FragmentRuleBrowseBinding
import ink.ddddd.flip.util.SpacesItemDecoration
import timber.log.Timber
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpList()
        setUpToolbar()
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