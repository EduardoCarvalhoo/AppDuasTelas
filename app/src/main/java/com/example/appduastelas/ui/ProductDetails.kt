package com.example.appduastelas.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appduastelas.R
import com.example.appduastelas.databinding.ActivityProductDetailsBinding

class ProductDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureDetailsData()
    }

    private fun configureDetailsData(){
        val imageUrl = intent.getStringExtra(IMAGE_URL)
        getUrl(imageUrl)
        binding.productDetailsDescriptionTextView.text = intent.getStringExtra(NAME) ?: ""
        binding.productDetailsValueTextView.text = intent.getStringExtra(PRICE) ?: ""
        binding.productDetailsNumberOfProductsTextView.text = intent.getStringExtra(STOCK) ?: "" + getString(R.string.cart_items_stock_text)
        binding.productDetailsItemInformationTextView.text = intent.getStringExtra(DESCRIPTION) ?: ""
    }

    private fun getUrl(imageUrl: String?) {
        Glide.with(this@ProductDetails).load(imageUrl).into(binding.productDetailsItemImageView)
    }

    companion object {
        private const val IMAGE_URL = "image_url"
        private const val NAME = "name"
        private const val PRICE = "price"
        private const val STOCK = "stock"
        private const val DESCRIPTION = "description"
        fun getStartIntent(
            context: Context, image_url: String?, name: String?, price: String?,
            stock: String?, description: String?
        ): Intent {
            return Intent(context, ProductDetails::class.java).apply {
                putExtra(IMAGE_URL, image_url)
                putExtra(NAME, name)
                putExtra(PRICE, price)
                putExtra(STOCK, stock)
                putExtra(DESCRIPTION, description)
            }
        }
    }
}