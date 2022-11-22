package com.example.budgettracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Labels::class], version = 1)
abstract class LabelsDatabase: RoomDatabase() {
    abstract fun LabelsDAO(): LabelsDAO
}