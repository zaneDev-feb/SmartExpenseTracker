package com.example.smartexpensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartexpensetracker.data.model.Transaction

// ==========================================
// DAO (Tờ lệnh thao tác với Database)
// ==========================================
@Dao
interface TransactionDao {
    // Thêm 1 giao dịch và trả về ID thật do Room tự sinh
    @Insert
    suspend fun insertTransaction(transaction: Transaction): Long

    // Xóa giao dịch dựa theo Khóa chính (id)
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    // Lấy toàn bộ giao dịch, sắp xếp mới nhất lên đầu
    @Query("SELECT * FROM bang_giao_dich ORDER BY id DESC")
    suspend fun getAllTransactions(): List<Transaction>
}
