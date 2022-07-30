package com.example.appduastelas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appduastelas.R
import com.example.appduastelas.adapter.ShoppingListAdapter
import com.example.appduastelas.databinding.ActivityMainBinding
import com.example.appduastelas.response.ItemsResponse
import com.example.appduastelas.utils.RetrofitConfig
import com.example.appduastelas.utils.showAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        requestProductData()
    }

    private fun setupRecyclerView() {
        with(binding.mainRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@MainActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    private fun requestProductData() {
        binding.mainProgressBar.isVisible = true
        val productsService = RetrofitConfig.getRetrofit().getMyProducts()
        val call: Call<List<ItemsResponse>> = productsService

        call.enqueue(object : Callback<List<ItemsResponse>> {
            override fun onResponse(
                call: Call<List<ItemsResponse>>,
                response: Response<List<ItemsResponse>>
            ) {
                binding.mainProgressBar.isVisible = false
                handleProductDataResponse(response)
                calculateCartItemValue(response)
            }

            override fun onFailure(call: Call<List<ItemsResponse>>, t: Throwable) {
                binding.mainProgressBar.isVisible = false
                handleProductDataRequestFailures(t)
            }
        })
    }

    private fun handleProductDataRequestFailures(throwable: Throwable) {
        if (throwable is IOException) {
            showAlertDialog(getString(R.string.no_internet_connection_error)) {
                requestProductData()
            }
        } else {
            showAlertDialog(getString(R.string.generic_error)) {
                requestProductData()
            }
        }
    }

    private fun handleProductDataResponse(response: Response<List<ItemsResponse>>) {
        if (response.isSuccessful && response.body() != null) {
            binding.mainRecyclerView.adapter = ShoppingListAdapter(
                response.body() ?: return
            ) { itemsResponse ->
                val intent = ProductDetails.getStartIntent(
                    this@MainActivity, itemsResponse.image_url, itemsResponse.name,
                    itemsResponse.price, itemsResponse.stock, itemsResponse.description
                )
                startActivity(intent)
            }
        } else {
            showAlertDialog(getString(R.string.server_error))
            requestProductData()
        }
    }

    private fun calculateCartItemValue(response: Response<List<ItemsResponse>>) {
        var subtotal = 0
        var total: Int
        response.body()?.forEach { item ->
            with(item) {
                subtotal += price?.toInt() ?: 0
                total = subtotal + (shipping?.toInt() ?: 0) + (tax?.toInt() ?: 0)
            }
            with(binding) {
                mainValueTotalTextView.text = total.toString()
                mainValueSubtotalTextView.text = subtotal.toString()
                mainValueShippingTextView.text = item.shipping
                mainValueTaxTextView.text = item.tax
            }
        }
    }
}