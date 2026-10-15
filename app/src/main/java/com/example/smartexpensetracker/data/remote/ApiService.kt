package com.example.smartexpensetracker.data.remote

import com.example.smartexpensetracker.data.model.ExchangeRateResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ==========================================
// MẠNG (Tờ hợp đồng API với Retrofit)
// ==========================================
interface ApiService {
    @GET("v4/latest/USD")
    suspend fun getRates(): ExchangeRateResponse
}

// Cỗ máy kết nối mạng tập trung (Singleton)
object RetrofitClient {
    private const val BASE_URL = "https://api.exchangerate-api.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
