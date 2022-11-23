package com.example.budgettracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase:RoomDatabase() {
    abstract fun AccountDAO(): AccountDAO
}