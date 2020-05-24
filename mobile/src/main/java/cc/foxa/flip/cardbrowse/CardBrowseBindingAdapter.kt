package cc.foxa.flip.cardbrowse

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.Rule

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

