package com.example.budgettracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.YearMonth

@Dao
interface ExpensesDAO {

    @Insert
    suspend fun insertExpense(expense: Expenses)

    @Update
    suspend fun updateExpense(expense: Expenses)

    @Query("DELETE FROM table_expenses where id LIKE :id")
    suspend fun deleteExpense(id: Long)

    @Query("SELECT DISTINCT yearMonth FROM table_expenses ORDER BY year DESC, month DESC")
    fun getYearMonth() : LiveData<List<String>>

    @Query("SELECT * FROM table_expenses WHERE yearMonth LIKE :yearMonth ORDER BY date DESC, expense DESC")
    fun getExpenses(yearMonth: String) : LiveData<List<Expenses>>
}