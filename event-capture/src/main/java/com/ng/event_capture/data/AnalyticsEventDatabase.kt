package com.ng.event_capture.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnalyticsEventDao::class], version = 3, exportSchema = false)
internal abstract class AnalyticsEventDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "analyticsEventsDatabase.db"
    }

    abstract fun daoAccess(): AnalyticsEventDaoAccess
}