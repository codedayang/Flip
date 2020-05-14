package ink.ddddd.flip.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ink.ddddd.flip.R
import ink.ddddd.flip.databinding.FragmentTempBinding
import ink.ddddd.flip.shared.EventObserver
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.domain.card.UpdateCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TempFragment : DaggerFragment() {

    @Inject lateinit var factory : ViewModelProvider.Factory

    private val viewModel by viewModels<TempViewModel> { factory }

    private lateinit var binding: FragmentTempBinding


    val tag1 = Tag(name = "Tag1")
    var card = Card(front = "Front", back = "Bar", tags = listOf(tag1))
    var id = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTempBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.card.observe(viewLifecycleOwner, Observer {  })
        setUpSnackBar()
//        setUpEvents()
        return binding.root
    }

//    private fun setUpEvents() {
//        binding.go.setOnClickListener {
//            val action = TempFragmentDirections.actionTempFragmentToCardEditFragment(id)
//            findNavController().navigate(action)
//        }
//        binding.populate.setOnClickListener {
//            viewModel.inflate(tag1, card)
//            id = card.id
//            card = Card(front = "Front", back = "Bar", tags = listOf(tag1))
//        }
//
//        binding.toCardBrowser.setOnClickListener {
//            val action = TempFragmentDirections.actionTempFragmentToCardBrowseFragment()
//            findNavController().navigate(action)
//        }
//
//        binding.toPerform.setOnClickListener {
//            val action = TempFragmentDirections.actionTempFragmentToPerformFragment()
//            findNavController().navigate(action)
//        }
//
//        binding.toRuleBrowser.setOnClickListener {
//            val action = TempFragmentDirections.actionTempFragmentToRuleBrowseFragment()
//            findNavController().navigate(action)
//        }
//
//        binding.toRuleEditor.setOnClickListener {
////            val action = TempFragmentDirections.actionTempFragmentToRuleEditFragment()
////            findNavController().navigate(action)
//        }
//
//        binding.toTagBrowser.setOnClickListener {
//            val action = TempFragmentDirections.actionTempFragmentToTagBrowseFragment()
//            findNavController().navigate(action)
//        }
//    }

    private fun setUpSnackBar() {
        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }
}