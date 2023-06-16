package com.aplimelta.coffeebidapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplimelta.coffeebidapp.data.source.model.ProductModel
import com.aplimelta.coffeebidapp.databinding.ItemRowCoffeebidBinding
import com.aplimelta.coffeebidapp.utils.load

class CoffeeAdapter : ListAdapter<ProductModel, CoffeeAdapter.CoffeeViewHolder>(
    CoffeeAdapterComparator()
) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(product: ProductModel)
    }

    inner class CoffeeViewHolder(private val binding: ItemRowCoffeebidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            binding.apply {
                tvItemTitle.text = item.name
                tvItemPrice.text = item.openPrice.toString()
                tvItemDate.text = item.startDate

                sivItemPhoto.load(item.productPict.toString())
            }
        }
    }

    class CoffeeAdapterComparator : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(
            oldItem: ProductModel,
            newItem: ProductModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductModel,
            newItem: ProductModel,
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
                onItemClickCallback.onItemClicked(currentItem)
            }
        }
    }

}