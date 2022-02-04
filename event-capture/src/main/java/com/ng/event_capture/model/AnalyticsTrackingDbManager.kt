package com.ng.event_capture.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ng.event_capture.data.AnalyticsEventDao
import com.ng.event_capture.data.AnalyticsEventDatabase
import com.ng.event_capture.repository.AnalyticsEventsRepository
import java.util.*


internal class AnalyticsTrackingDbManager(val context: Context) {

    var analyticsDb: AnalyticsEventDatabase
    var repository: AnalyticsEventsRepository
    var mNotificationHelper: NotificationHelper
    var filteredList: MutableLiveData<List<AnalyticsEventDao>> = MutableLiveData()
    var allEventsList: MutableLiveData<List<AnalyticsEventDao>> = MutableLiveData()
    var eventPropertiesList: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    var eventName: MutableLiveData<String> = MutableLiveData()

    init {
        analyticsDb = Room.databaseBuilder(
                context,
                AnalyticsEventDatabase::class.java,
                AnalyticsEventDatabase.DATABASE_NAME
        )
                .fallbackToDestructiveMigration().allowMainThreadQueries().build()
        repository = AnalyticsEventsRepository(analyticsDb.daoAccess())
        repository.deleteAllData()
        mNotificationHelper = NotificationHelper(context)
        showNotification(false)
    }

    fun insertAnalyticsEvent(eventName: String, eventProperties: Map<String, String>) {
        repository.insertAnalyticEvents(eventName, eventProperties)
        showNotification(false)
    }

    fun fetchAllEvents() {
        allEventsList.postValue(repository.fetchAllEvents())
    }

    fun fetchAllEventProperties(id: Long) {
        eventPropertiesList.value = repository.getEventPropertiesJSON(id)
    }

    fun searchEvents(s: String, list: MutableLiveData<List<AnalyticsEventDao>>) {
        val itemList = ArrayList<AnalyticsEventDao>()
        list.value?.let {
            if (it.isNotEmpty()) {
                for (item in it) {
                    if (item.eventName.lowercase(Locale.ROOT)
                            .startsWith(s.lowercase(Locale.ROOT))
                    ) {
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

    fun deleteParticularData(analyticsEventDao: AnalyticsEventDao){
        repository.deleteRowData(analyticsEventDao)
        fetchAllEvents()
    }

    fun getEventPropertiesAsText(eventName: String, eventProperties: HashMap<String, String>?): String {
        val eventProperty: Map<String, String> = LinkedHashMap(eventProperties)
        var eventString = ""
        for (key: String in eventProperty.keys) {
            eventString = eventString + key + "  " + eventProperty[key] + "\n"
        }
        return eventName + "\n" + eventString
    }

    fun getAllEventPropertiesAsText(): String? {
        return allEventsList.value?.let {
             repository.getAllEventsAsJSON(it)
        }
    }


    fun showNotification(sticky: Boolean) {
        fetchAllEvents()
        allEventsList.value?.let {
            if (it.isNotEmpty()) {
                mNotificationHelper.setUp()
                mNotificationHelper.show(sticky, allEventsList)
            }
        }
    }

    fun dismiss(){
        mNotificationHelper.dismiss()
    }

    fun sort(events: List<AnalyticsEventDao>): List<AnalyticsEventDao> {
        return ArrayList(events).sortedWith(compareByDescending<AnalyticsEventDao> { it.timeStamp })
    }
}