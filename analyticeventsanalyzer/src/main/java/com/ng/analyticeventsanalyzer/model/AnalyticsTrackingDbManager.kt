package com.ng.analyticeventsanalyzer.model

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ng.analyticeventsanalyzer.data.AnalyticsEventDao
import com.ng.analyticeventsanalyzer.data.AnalyticsEventDatabase
import com.ng.analyticeventsanalyzer.repository.AnalyticsEventsRepository
import com.ng.analyticeventsanalyzer.ui.AnalyticsEventsListActivity
import java.util.*
import kotlin.collections.ArrayList

class AnalyticsTrackingDbManager(val context: Context) {

    var analyticsDb: AnalyticsEventDatabase
    var repository: AnalyticsEventsRepository
    var mNotificationHelper: NotificationHelper
    var filteredList: MutableLiveData<List<AnalyticsEventDao>> = MutableLiveData()
    var allEventsList: MutableLiveData<List<AnalyticsEventDao>> = MutableLiveData()
    var eventPropertiesList: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    companion object {
        var instance: AnalyticsTrackingDbManager? = null
    }

    init {
        AnalyticsEventDatabase
        analyticsDb = Room.databaseBuilder(
                context,
                AnalyticsEventDatabase::class.java,
                AnalyticsEventDatabase.DATABASE_NAME
        )
                .fallbackToDestructiveMigration().allowMainThreadQueries().build()
        repository = AnalyticsEventsRepository(analyticsDb.daoAccess())
        instance = this
        mNotificationHelper = NotificationHelper(context)
        showNotification(false)
    }

    fun insertAnalyticsEvent(eventName: String, eventProperties: Map<String, String>) {
        repository.insertAnalyticEvents(eventName, eventProperties)
    }

    fun fetchAllEvents() {
        allEventsList.value = repository.fetchAllEvents()
    }

    fun fetchAllEventProperties(eventName: String) {
        eventPropertiesList.value = repository.getEventPropertiesJSON(eventName)
    }

    fun getLaunchIntent(context: Context?): Intent? {
        return Intent(context, AnalyticsEventsListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    fun searchEvents(s: String, list: MutableLiveData<List<AnalyticsEventDao>>) {
        val itemList = ArrayList<AnalyticsEventDao>()
        list.value?.let {
            if (it.isNotEmpty()) {
                for (item in it) {
                    if (item.eventName.toLowerCase(Locale.ROOT).startsWith(s.toLowerCase(Locale.ROOT))) {
                        itemList.add(item)
                    }
                }
            }
        }
        filteredList.value = itemList
    }

    fun deleteAllEvents() {
        repository.deleteAllData()
        allEventsList.value = mutableListOf()
    }

    fun getEventPropertiesAsText(eventProperties: HashMap<String, String>?): String {
        val eventProperty: Map<String, String> = LinkedHashMap(eventProperties)
        var eventString: String = ""
        for (key: String in eventProperty.keys) {
            eventString = eventString + key + "  " + eventProperty[key] + "\n"
        }
        return eventString
    }

    fun getAllEventPropertiesAsText(): String {
        var eventString: String = ""
        allEventsList.value?.let{
            for (name: AnalyticsEventDao in allEventsList.value!!)
                eventString
        }

        return eventString
    }

    fun showNotification(sticky: Boolean){
        fetchAllEvents()
        mNotificationHelper.setUp()
        mNotificationHelper.show(sticky, allEventsList)
    }

    fun dismiss(){
        mNotificationHelper.dismiss()
    }
}