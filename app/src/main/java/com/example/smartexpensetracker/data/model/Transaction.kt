package com.example.smartexpensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// ==========================================
// MODEL (Khuôn mẫu dữ liệu giao dịch)
// ==========================================
@Entity(tableName = "bang_giao_dich")
data class Transaction(
    // Bắt buộc có 1 cột ID (Khóa chính) để định danh từng dòng
    // autoGenerate = true: Room sẽ tự động cấp số thứ tự 1, 2, 3...
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Double,
    val isIncome: Boolean
)
