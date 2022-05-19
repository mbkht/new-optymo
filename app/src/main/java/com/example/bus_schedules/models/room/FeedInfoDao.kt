package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.FeedInfo

@Dao
interface FeedInfoDao {
    @Insert
    fun insertAll(vararg feedInfo: FeedInfo)

    @Query("DELETE FROM feed_info")
    fun deleteAll()
}