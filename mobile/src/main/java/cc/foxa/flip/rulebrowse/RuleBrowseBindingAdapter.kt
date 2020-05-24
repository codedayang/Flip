package cc.foxa.flip.rulebrowse

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.foxa.flip.shared.data.model.Rule

@BindingAdapter(value = ["rules", "viewModel"], requireAll = true)
fun ruleList(recyclerView: RecyclerView, rules: List<Rule>?, viewModel: RuleBrowseViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = RuleBrowseAdapter(viewModel)
    }
    if (rules.isNullOrEmpty()) {
        recyclerView.isVisible = false
    } else {
        recyclerView.isVisible = true
        (recyclerView.adapter as RuleBrowseAdapter).submitList(rules)
    }
}