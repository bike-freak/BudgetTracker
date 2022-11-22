package com.example.budgettracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_expenses")
data class Expenses(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val expense: Int,
    val label: String,
    val yearMonth: String,
    val date: Int,
    val month: Int,
    val year: Int
)