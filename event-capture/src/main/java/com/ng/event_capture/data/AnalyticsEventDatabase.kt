package com.ng.event_capture.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ng.event_capture.data.AnalyticsEventDao
import com.ng.event_capture.data.AnalyticsEventDaoAccess

@Database(entities = [AnalyticsEventDao::class], version = 1, exportSchema = false)
abstract class AnalyticsEventDatabase : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "analyticsEventsDatabase.db"
    }

    abstract fun daoAccess(): AnalyticsEventDaoAccess
}