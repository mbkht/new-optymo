package com.example.bus_schedules.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.data.models.Agency

@Dao
interface AgencyDao {
    @Insert
    fun insertAll(vararg agencies: Agency)

    @Query("DELETE FROM agency")
    fun deleteAll()
}