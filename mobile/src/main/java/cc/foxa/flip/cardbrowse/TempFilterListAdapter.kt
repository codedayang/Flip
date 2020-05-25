package cc.foxa.flip.cardbrowse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.foxa.flip.databinding.ItemFilterBinding
import cc.foxa.flip.shared.Event
import cc.foxa.flip.shared.filter.Filter

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
        binding.chip.text = filter.toString()
        binding.chip.setOnCloseIconClickListener {
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