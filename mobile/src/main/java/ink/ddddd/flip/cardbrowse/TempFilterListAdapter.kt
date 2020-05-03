package ink.ddddd.flip.cardbrowse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ink.ddddd.flip.databinding.ItemFilterBinding
import ink.ddddd.flip.shared.Event
import ink.ddddd.flip.shared.filter.Filter

class TempFilterListAdapter(private val viewModel: CardBrowseViewModel)
    : ListAdapter<Filter, TempFilterViewHolder>(TempFilterDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempFilterViewHolder {
        return TempFilterViewHolder(
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), null, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: TempFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class TempFilterViewHolder(
    private val binding: ItemFilterBinding,
    private val viewModel: CardBrowseViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(filter: Filter) {
        binding.text.text = filter.toString()
        binding.delete.setOnClickListener {
            val position = adapterPosition
            viewModel.tempRuleFilters.remove(filter)
            viewModel.refreshTempRuleFilter.value = Event(Unit)
        }
    }
}

object TempFilterDiffer : DiffUtil.ItemCallback<Filter>() {
    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}