package com.example.bus_schedules.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.data.models.Transfer

@Dao
interface TransferDao {
    @Insert
    fun insertAll(vararg transfers: Transfer)

    @Query("DELETE FROM transfers")
    fun deleteAll()
}