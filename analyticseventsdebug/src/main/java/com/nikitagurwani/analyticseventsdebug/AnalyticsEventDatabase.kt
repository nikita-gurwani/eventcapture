package com.nikitagurwani.analyticseventsdebug

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnalyticsEventDao::class], version = 1, exportSchema = false)
abstract class AnalyticsEventDatabase : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "analyticsEventsDatabase.db"
    }

    abstract fun daoAccess(): AnalyticsEventDaoAccess
}