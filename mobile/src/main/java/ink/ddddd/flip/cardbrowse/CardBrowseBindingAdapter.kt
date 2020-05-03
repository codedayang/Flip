package ink.ddddd.flip.cardbrowse

import android.view.LayoutInflater
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ink.ddddd.flip.R
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Rule
import timber.log.Timber

@BindingAdapter(value = ["items", "viewModel"], requireAll = true)
fun cardBrowseList(
    recyclerView: RecyclerView,
    list: List<Card>?,
    viewmodel: CardBrowseViewModel
) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = CardBrowseAdapter(viewmodel)
    }

    if (list.isNullOrEmpty()) {
        recyclerView.isVisible = false
    } else {
        recyclerView.isVisible = true
        (recyclerView.adapter as CardBrowseAdapter).submitList(list)
    }
}

