package com.example.appduastelas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appduastelas.databinding.CartItemsBinding
import com.example.appduastelas.response.ItemsResponse

class ShoppingListAdapter(
    private var productList: List<ItemsResponse>,
    private val onItemClickListener: (item: ItemsResponse) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = CartItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ShoppingListViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bindView(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ShoppingListViewHolder(
        binding: CartItemsBinding,
        private val onItemClickListener: (item: ItemsResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.cartItemsProductImageView
        private val description = binding.cartItemsDescriptionTextView
        private val price = binding.cartItemsPriceOfTheProductTextView
        private val stock = binding.cartItemsStockTextView

        fun bindView(item: ItemsResponse) {
            getUrl(item.image_url.toString())
            description.text = item.name
            price.text = item.price
            stock.text = item.stock

            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }

        private fun getUrl(imageUrl: String) {
            Glide.with(this@ShoppingListViewHolder.itemView).load(imageUrl).into(image)
        }
    }
}