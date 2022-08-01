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

        configureActionBar()
        configureDetailsData()
    }

    private fun configureDetailsData() {
        Glide.with(this@ProductDetails).load(intent.getStringExtra(IMAGE_URL))
            .into(binding.productDetailsItemImageView)
        with(binding) {
            productDetailsDescriptionTextView.text = intent.getStringExtra(NAME) ?: ""
            productDetailsValueTextView.text = intent.getStringExtra(PRICE) ?: ""
            productDetailsNumberOfProductsTextView.text =
                getString(R.string.cart_items_stock_text, intent.getStringExtra(STOCK))
            productDetailsItemInformationTextView.text = intent.getStringExtra(DESCRIPTION) ?: ""
        }
    }

    companion object {
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
        private const val IMAGE_URL = "image_url"
        private const val NAME = "name"
        const val PRICE = "price"
        private const val STOCK = "stock"
        private const val DESCRIPTION = "description"
    }

    private fun configureActionBar() {
        setSupportActionBar(binding.productDetailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
    }
}