package com.ng.analyticstracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ng.analyticstracker.AnalyticsEventDao

@Dao
interface AnalyticsEventDaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnalyticEventDAO(analyticsEventDao: AnalyticsEventDao)

    @Query("SELECT * FROM AnalyticsEventTable")
    fun fetchAllData(): List<AnalyticsEventDao>

    @Query("SELECT * FROM AnalyticsEventTable as event WHERE event.eventName =:name")
    fun getEventPropertiesJson(name: String) : AnalyticsEventDao

    @Query("DELETE FROM AnalyticsEventTable")
    fun clearAllTheData()
}