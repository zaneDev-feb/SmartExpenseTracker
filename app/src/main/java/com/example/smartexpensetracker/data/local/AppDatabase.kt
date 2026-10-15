package com.example.smartexpensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartexpensetracker.data.model.Transaction

// ==========================================
// TRỤ SỞ DATABASE (Room Database)
// ==========================================
@Database(entities = [Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
