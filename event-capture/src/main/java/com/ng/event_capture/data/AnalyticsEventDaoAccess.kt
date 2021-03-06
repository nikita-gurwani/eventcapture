package com.ng.event_capture.data

import androidx.room.*

@Dao
interface AnalyticsEventDaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnalyticEventDAO(analyticsEventDao: AnalyticsEventDao)

    @Query("SELECT * FROM AnalyticsEventTable")
    fun fetchAllData(): List<AnalyticsEventDao>

    @Query("SELECT * FROM AnalyticsEventTable as event WHERE event.id =:id")
    fun getEventPropertiesJson(id: Long): AnalyticsEventDao

    @Query("DELETE FROM AnalyticsEventTable")
    fun clearAllTheData()

    @Delete
    fun delete(analyticsEventDao: AnalyticsEventDao)

    @Query("SELECT * FROM AnalyticsEventTable as event WHERE event.eventName =:name")
    fun getEventsByName(name: String): List<AnalyticsEventDao>

}