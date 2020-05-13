package ink.ddddd.flip.ruleedit

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ink.ddddd.flip.shared.filter.Filter

@BindingAdapter(value = ["filters", "viewModel"], requireAll = true)
fun filters(recyclerView: RecyclerView, filters: List<Filter>?, viewModel: RuleEditViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = FilterListAdapter(viewModel)
    }
    recyclerView.isVisible = true
    (recyclerView.adapter as FilterListAdapter).submitList(filters)
}