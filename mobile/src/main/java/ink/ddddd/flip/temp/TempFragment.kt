package ink.ddddd.flip.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    val card = Card(front = "Front", back = "Bar", tags = listOf(tag1))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTempBinding.inflate(inflater, container, false)
        setUpSnackBar()
        setUpEvents()
        return binding.root
    }

    private fun setUpEvents() {
        binding.go.setOnClickListener {
            val action = TempFragmentDirections.actionTempFragmentToCardEditFragment(card.id)
            findNavController().navigate(action)
        }
        binding.populate.setOnClickListener {
            viewModel.inflate(tag1, card)
        }
    }

    private fun setUpSnackBar() {
        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }
}