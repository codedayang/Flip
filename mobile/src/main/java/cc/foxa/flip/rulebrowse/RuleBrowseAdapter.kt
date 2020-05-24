package cc.foxa.flip.rulebrowse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.foxa.flip.databinding.ItemRuleBinding
import cc.foxa.flip.shared.data.model.Rule

class RuleBrowseAdapter(
    private val viewModel: RuleBrowseViewModel
) : ListAdapter<Rule, RuleBrowseViewHolder>(RuleDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleBrowseViewHolder {
        return RuleBrowseViewHolder(
            ItemRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: RuleBrowseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
class RuleBrowseViewHolder(
    private val binding: ItemRuleBinding,
    private val ruleBrowseActionHandler: RuleBrowseActionHandler
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(rule: Rule) {
        binding.rule = rule
        binding.actionHandler = ruleBrowseActionHandler
        binding.executePendingBindings()
    }
}

object RuleDiffer : DiffUtil.ItemCallback<Rule>() {
    override fun areItemsTheSame(oldItem: Rule, newItem: Rule): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Rule, newItem: Rule): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}