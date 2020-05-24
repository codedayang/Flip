package cc.foxa.flip.cardbrowse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cc.foxa.flip.databinding.ItemCardBrowseResultBinding
import cc.foxa.flip.shared.data.model.Card

class CardBrowseAdapter(
    private val viewModel: CardBrowseViewModel
) : ListAdapter<Card, CardBrowseViewHolder>(CardBrowseDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardBrowseViewHolder {
        return CardBrowseViewHolder(
            ItemCardBrowseResultBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: CardBrowseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CardBrowseViewHolder(
    private val binding: ItemCardBrowseResultBinding,
    private val viewModel: CardBrowseViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: Card) {
        binding.eventListener = viewModel
        binding.card = card
        binding.executePendingBindings()
    }

}

object CardBrowseDiff : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        oldItem.tags.forEach {
            if (!newItem.tags.contains(it)) return false
        }
        newItem.tags.forEach {
            if (!oldItem.tags.contains(it)) return false
        }
        return oldItem.front.contentEquals(newItem.front)
                && oldItem.back.contentEquals(newItem.back)
                && oldItem.priority == newItem.priority
    }

}