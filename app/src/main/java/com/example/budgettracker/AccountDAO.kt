package com.example.budgettracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDAO {
    @Insert
    suspend fun insertAcc(account: Account)

    @Update
    suspend fun updateAcc(account: Account)

    @Query("SELECT amount from table_account where id like :id")
    suspend fun getAmount(id: Long) : Long

    @Query("SELECT EXISTS(SELECT * FROM table_account WHERE id LIKE :id)")
    suspend fun isRowExists(id: Long): Boolean
}