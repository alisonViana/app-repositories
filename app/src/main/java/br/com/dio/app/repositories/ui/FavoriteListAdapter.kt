package br.com.dio.app.repositories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.app.repositories.data.model.Favorite
import br.com.dio.app.repositories.databinding.ItemFavoriteBinding
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class FavoriteListAdapter: ListAdapter<Favorite, FavoriteListAdapter.ViewHolder>(DiffCallBackFavorite()) {

    var deleteFavoriteListener: (Favorite) -> Unit = {}
    var clickFavoriteListener: (Favorite) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemFavoriteBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Favorite) {
            binding.tvOwner.text = item.userName
            Glide.with(binding.root.context)
                .load(item.userAvatar)
                .into(binding.ivOwner)

            binding.btnDelete.setOnClickListener {
                deleteFavoriteListener(item)
            }
            binding.favoriteCard.setOnClickListener {
                clickFavoriteListener(item)
            }
        }
    }

}

class DiffCallBackFavorite : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = oldItem.id == newItem.id
}