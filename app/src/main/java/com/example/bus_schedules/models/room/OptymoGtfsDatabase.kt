package com.example.bus_schedules.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bus_schedules.models.*

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