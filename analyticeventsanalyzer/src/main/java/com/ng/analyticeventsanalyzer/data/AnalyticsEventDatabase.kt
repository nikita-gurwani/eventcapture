package com.ng.analyticeventsanalyzer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ng.analyticeventsanalyzer.data.AnalyticsEventDao
import com.ng.analyticeventsanalyzer.data.AnalyticsEventDaoAccess

@Database(entities = [AnalyticsEventDao::class], version = 1, exportSchema = false)
abstract class AnalyticsEventDatabase : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "analyticsEventsDatabase.db"
    }

    abstract fun daoAccess(): AnalyticsEventDaoAccess
}