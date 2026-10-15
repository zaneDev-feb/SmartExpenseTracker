package com.example.smartexpensetracker.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.smartexpensetracker.data.local.AppDatabase
import com.example.smartexpensetracker.data.model.Transaction
import com.example.smartexpensetracker.data.remote.RetrofitClient
import kotlinx.coroutines.launch

// ==========================================
// VIEWMODEL (Bộ não giữ State và xử lý Logic)
// ==========================================
class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Khởi tạo Database
    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "expense_database"
    ).build()

    private val dao = database.transactionDao()

    // 2. Các State giao diện
    var balance by mutableStateOf(15000000.0)
        private set
    var income by mutableStateOf(15000000.0)
        private set
    var expense by mutableStateOf(0.0)
        private set
    var tyGiaVND by mutableStateOf(0.0)
        private set

    val history = mutableStateListOf<Transaction>()

    // 3. Khởi chạy: Tự động tải từ Database và lấy tỷ giá
    init {
        viewModelScope.launch {
            val savedTransactions = dao.getAllTransactions()
            history.addAll(savedTransactions)

            val tongThu = savedTransactions.filter { it.isIncome }.sumOf { it.amount }
            val tongChi = savedTransactions.filter { !it.isIncome }.sumOf { it.amount }

            income = 15000000.0 + tongThu
            expense = tongChi
            balance = income - expense
        }

        capNhatTyGia()
    }

    // 4. Lấy tỷ giá từ API
    fun capNhatTyGia() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRates()
                tyGiaVND = response.rates["VND"] ?: 0.0
            } catch (e: Exception) {
                println("Lỗi tải tỷ giá: ${e.message}")
            }
        }
    }

    // 5. Thêm giao dịch mới
    fun addTransaction(title: String, amount: Double, isIncome: Boolean) {
        if (isIncome) {
            income += amount
            balance += amount
        } else {
            expense += amount
            balance -= amount
        }

        viewModelScope.launch {
            val newTx = Transaction(title = title, amount = amount, isIncome = isIncome)
            val generatedId = dao.insertTransaction(newTx)
            // Nhét lên đầu danh sách hiển thị với ID thật do Room cấp
            history.add(0, newTx.copy(id = generatedId.toInt()))
        }
    }

    // 6. Xóa giao dịch
    fun deleteTransaction(transaction: Transaction) {
        if (transaction.isIncome) {
            income -= transaction.amount
            balance -= transaction.amount
        } else {
            expense -= transaction.amount
            balance += transaction.amount
        }

        // Xóa khỏi UI
        history.remove(transaction)

        // Xóa vĩnh viễn khỏi Database
        viewModelScope.launch {
            dao.deleteTransaction(transaction)
        }
    }
}
