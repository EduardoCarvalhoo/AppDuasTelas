package com.example.appduastelas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appduastelas.adapter.ShoppingListAdapter
import com.example.appduastelas.databinding.ActivityMainBinding
import com.example.appduastelas.response.ItemsResponse
import com.example.appduastelas.utils.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        requestProducts()
    }

    private fun setupRecyclerView() {
        with(binding.mainRecyclerView) {
            layoutManager = LinearLayoutManager(
                this@MainActivity, RecyclerView.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    private fun requestProducts() {
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
            }

            override fun onFailure(call: Call<List<ItemsResponse>>, t: Throwable) {

            }
        })
    }

    private fun handleProductDataResponse(response: Response<List<ItemsResponse>>) {
        if (response.isSuccessful && response.body() != null) {
            binding.mainRecyclerView.adapter = ShoppingListAdapter(
                response.body() ?: return
            ) {

            }
        }
    }
}