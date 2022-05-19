package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.Transfer

@Dao
interface TransferDao {
    @Insert
    fun insertAll(vararg transfers: Transfer)

    @Query("DELETE FROM transfers")
    fun deleteAll()
}