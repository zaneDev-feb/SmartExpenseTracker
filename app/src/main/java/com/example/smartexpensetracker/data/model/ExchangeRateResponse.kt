package com.example.smartexpensetracker.data.model

// ==========================================
// Khuôn mẫu hứng dữ liệu JSON tỷ giá từ Retrofit
// ==========================================
data class ExchangeRateResponse(
    val base: String,
    val rates: Map<String, Double>
)
