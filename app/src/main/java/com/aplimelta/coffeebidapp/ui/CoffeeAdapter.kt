package com.aplimelta.coffeebidapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplimelta.coffeebidapp.data.source.remote.response.ProductResponseItem
import com.aplimelta.coffeebidapp.databinding.ItemRowCoffeebidBinding

class CoffeeAdapter : ListAdapter<ProductResponseItem, CoffeeAdapter.CoffeeViewHolder>(
    CoffeeAdapterComparator()
) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(productId: Int)
    }

    inner class CoffeeViewHolder(private val binding: ItemRowCoffeebidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductResponseItem) {
            binding.apply {

            }
        }
    }

    class CoffeeAdapterComparator : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductResponseItem,
            newItem: ProductResponseItem,
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        return CoffeeViewHolder(
            ItemRowCoffeebidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)

            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(currentItem.id)
            }
        }
    }

}