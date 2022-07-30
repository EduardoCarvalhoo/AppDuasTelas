package com.example.appduastelas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appduastelas.R
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
        private val stock = binding.cartItemsNumberOfProductsTextView

        fun bindView(item: ItemsResponse) {
            Glide.with(this@ShoppingListViewHolder.itemView).load(item.image_url).into(image)
            description.text = item.name
            price.text = item.price
            stock.text = itemView.context.getString(R.string.cart_items_stock_text, item.stock)

            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }
    }
}