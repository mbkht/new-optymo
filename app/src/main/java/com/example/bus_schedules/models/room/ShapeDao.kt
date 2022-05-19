package com.example.bus_schedules.models.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.Shape

@Dao
interface ShapeDao {
    @Insert
    fun insertAll(vararg shapes: Shape)

    @Query("DELETE FROM shapes")
    fun deleteAll()
}