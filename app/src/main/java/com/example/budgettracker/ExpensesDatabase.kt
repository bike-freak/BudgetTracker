package com.example.budgettracker

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Expenses::class],version = 1)
abstract class ExpensesDatabase: RoomDatabase() {
    abstract fun ExpensesDAO(): ExpensesDAO
}