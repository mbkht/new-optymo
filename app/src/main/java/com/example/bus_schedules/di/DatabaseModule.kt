package com.example.bus_schedules.di

import android.content.Context
import androidx.room.Room
import com.example.bus_schedules.data.database.OptymoGtfsDatabase
import com.example.bus_schedules.data.database.dao.RouteDao
import com.example.bus_schedules.data.database.dao.StopDao
import com.example.bus_schedules.data.database.dao.TripDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideRouteDao(appDatabase: OptymoGtfsDatabase): RouteDao {
        return appDatabase.routeDao()
    }

    @Provides
    fun provideStopDao(appDatabase: OptymoGtfsDatabase): StopDao {
        return appDatabase.stopDao()
    }

    @Provides
    fun provideTripDao(appDatabase: OptymoGtfsDatabase): TripDao {
        return appDatabase.tripDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): OptymoGtfsDatabase {
        return Room.databaseBuilder(appContext, OptymoGtfsDatabase::class.java, "OptymoGtfs.db")
            .createFromAsset("database/gtfs.db")
            .build()
    }
}