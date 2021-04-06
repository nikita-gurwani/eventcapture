package com.ng.analyticstracker

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ng.analyticstracker.AnalyticsEventDao
import com.ng.analyticstracker.AnalyticsEventDaoAccess

@Database(entities = [AnalyticsEventDao::class], version = 1, exportSchema = false)
abstract class AnalyticsEventDatabase : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "analyticsEventsDatabase.db"
    }

    abstract fun daoAccess(): AnalyticsEventDaoAccess
}