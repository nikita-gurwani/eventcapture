package com.nikitagurwani.analyticseventsdebug

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnalyticsEventDaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnalyticEventDAO(analyticsEventDao: AnalyticsEventDao)

    @Query("SELECT * FROM AnalyticsEventTable")
    fun fetchAllData(): List<AnalyticsEventDao>

    @Query("SELECT * FROM AnalyticsEventTable as event WHERE event.eventName =:name")
    fun getEventPropertiesJson(name: String) : AnalyticsEventDao
}