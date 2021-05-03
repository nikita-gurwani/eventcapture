package com.ng.event_capture.repository

import com.google.gson.Gson
import com.ng.event_capture.data.AnalyticsEventDao
import com.ng.event_capture.data.AnalyticsEventDaoAccess
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AnalyticsEventsRepository(val analyticsEventDaoAccess: AnalyticsEventDaoAccess) {

    fun insertAnalyticEvents(eventName: String, eventProperties: Map<String, String>) {
        val analyticsEventDao = AnalyticsEventDao(eventName, SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.getDefault()).format(Date()),
                JSONObject(eventProperties).toString())
        analyticsEventDaoAccess.insertAnalyticEventDAO(analyticsEventDao)
    }

    fun fetchAllEvents(): List<AnalyticsEventDao> {
        return analyticsEventDaoAccess.fetchAllData()
    }

    fun getEventPropertiesJSON(name: String): HashMap<String, String> {
        var map: Map<String, String> = HashMap()
        try {
            val analyticsEventDao: AnalyticsEventDao = analyticsEventDaoAccess.getEventPropertiesJson(name)
            map = Gson().fromJson(analyticsEventDao.eventProperties, map.javaClass)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map as HashMap<String, String>
    }

    fun getAllEventsAsJSON(events: List<AnalyticsEventDao>): String {
        var jsonString: String = ""
        try {
           jsonString = Gson().toJson(events)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonString
    }

    fun deleteAllData() {
        analyticsEventDaoAccess.clearAllTheData()
    }

    fun deleteRowData(analyticsEventDao: AnalyticsEventDao) {
        analyticsEventDaoAccess.delete(analyticsEventDao)
    }
}