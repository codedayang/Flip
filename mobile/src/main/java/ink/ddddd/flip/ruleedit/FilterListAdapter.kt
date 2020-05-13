package ink.ddddd.flip.ruleedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ink.ddddd.flip.databinding.ItemFilterBinding
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.filter.Filter
import ink.ddddd.flip.util.dip2px

class FilterListAdapter(private val viewModel: RuleEditViewModel)
    : ListAdapter<Filter, FilterViewHolder>(FilterDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), null, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class FilterViewHolder(
    private val binding: ItemFilterBinding,
    private val viewModel: RuleEditViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(filter: Filter) {
        binding.chip.textSize = 16F
        binding.chip.text = filter.toString()
        binding.chip.chipMinHeight = dip2px(binding.root.context, 52F)
        binding.chip.setOnCloseIconClickListener {
            viewModel.rule.value!!.filters -= filter
            viewModel.refreshFilterList.value = Event(Unit)
            viewModel.changed = true
        }
    }
}

object FilterDiffer : DiffUtil.ItemCallback<Filter>() {
    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}