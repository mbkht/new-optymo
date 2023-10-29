package com.example.bus_schedules.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bus_schedules.data.database.dao.AgencyDao
import com.example.bus_schedules.data.database.dao.CalendarDao
import com.example.bus_schedules.data.database.dao.CalendarDateDao
import com.example.bus_schedules.data.database.dao.FeedInfoDao
import com.example.bus_schedules.data.database.dao.RouteDao
import com.example.bus_schedules.data.database.dao.ShapeDao
import com.example.bus_schedules.data.database.dao.StopDao
import com.example.bus_schedules.data.database.dao.StopTimeDao
import com.example.bus_schedules.data.database.dao.TransferDao
import com.example.bus_schedules.data.database.dao.TripDao
import com.example.bus_schedules.data.models.Agency
import com.example.bus_schedules.data.models.Calendar
import com.example.bus_schedules.data.models.CalendarDate
import com.example.bus_schedules.data.models.FeedInfo
import com.example.bus_schedules.data.models.Route
import com.example.bus_schedules.data.models.Shape
import com.example.bus_schedules.data.models.Stop
import com.example.bus_schedules.data.models.StopTime
import com.example.bus_schedules.data.models.Transfer
import com.example.bus_schedules.data.models.Trip

@Database(
    entities = [
        Agency::class,
        Calendar::class,
        CalendarDate::class,
        FeedInfo::class,
        Route::class,
        Shape::class,
        Stop::class,
        StopTime::class,
        Transfer::class,
        Trip::class,
    ],
    version = 1
)
abstract class OptymoGtfsDatabase : RoomDatabase() {

    abstract fun agencyDao(): AgencyDao

    abstract fun calendarDao(): CalendarDao

    abstract fun calendarDateDao(): CalendarDateDao

    abstract fun feedInfoDao(): FeedInfoDao

    abstract fun routeDao(): RouteDao

    abstract fun shapeDao(): ShapeDao

    abstract fun stopDao(): StopDao

    abstract fun stopTimeDao(): StopTimeDao

    abstract fun transferDao(): TransferDao

    abstract fun tripDao(): TripDao
}