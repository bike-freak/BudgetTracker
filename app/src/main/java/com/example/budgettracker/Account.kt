package com.example.budgettracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_account")
data class Account(
    @PrimaryKey(autoGenerate = true) val id:Long?,
    val amount: Long
)