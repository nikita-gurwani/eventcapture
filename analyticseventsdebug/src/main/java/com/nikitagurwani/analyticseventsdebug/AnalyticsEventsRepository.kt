package com.nikitagurwani.analyticseventsdebug

import com.google.gson.Gson
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
}