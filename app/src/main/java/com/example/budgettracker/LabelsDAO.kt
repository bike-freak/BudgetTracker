package com.example.budgettracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface LabelsDAO {

    @Insert
    suspend fun insertLabel(label: Labels)

    @Update
    suspend fun updateLabel(label: Labels)

    @Query("DELETE FROM table_labels where id LIKE :id")
    suspend fun deleteLabel(id: Long)

    @Query("SELECT * FROM table_labels")
    fun getLabel() : LiveData<List<Labels>>

    @Query("SELECT * FROM table_labels")
    fun getLabels() : List<Labels>

}