package com.example.budgettracker

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="table_labels")
data class Labels (
    @PrimaryKey(autoGenerate = true) val id:Long?,
    val labelName: String
)