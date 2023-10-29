package com.example.bus_schedules.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.data.models.Shape

@Dao
interface ShapeDao {
    @Insert
    fun insertAll(vararg shapes: Shape)

    @Query("DELETE FROM shapes")
    fun deleteAll()
}