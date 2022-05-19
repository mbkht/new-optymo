package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.Agency

@Dao
interface AgencyDao {
    @Insert
    fun insertAll(vararg agencies: Agency)

    @Query("DELETE FROM agency")
    fun deleteAll()
}