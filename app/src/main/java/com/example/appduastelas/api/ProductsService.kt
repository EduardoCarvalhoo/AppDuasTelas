package com.example.appduastelas.api

import com.example.appduastelas.response.ItemsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductsService {
    @GET("myfreecomm/desafio-mobile-android/master/api/data.json")
    fun getMyProducts(): Call<List<ItemsResponse>>
}