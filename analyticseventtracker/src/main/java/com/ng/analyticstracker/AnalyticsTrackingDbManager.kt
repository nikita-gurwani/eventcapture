package com.ng.analyticstracker

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AnalyticsTrackingDbManager(val context: Context) {

    var analyticsDb: AnalyticsEventDatabase
    var repository: AnalyticsEventsRepository

    var filteredList: MutableLiveData<List<AnalyticsEventDao>> = MutableLiveData()

    companion object {
        lateinit var instance: AnalyticsTrackingDbManager
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
    }

    fun insertAnalyticsEvent(eventName: String, eventProperties: Map<String, String>) {
        repository.insertAnalyticEvents(eventName, eventProperties)
    }

    fun fetchAllEvents(): List<AnalyticsEventDao> {
        return repository.fetchAllEvents()
    }

    fun fetchAllEventProperties(eventName: String): HashMap<String, String> {
        return repository.getEventPropertiesJSON(eventName)
    }

    fun getLaunchIntent(context: Context?): Intent? {
        return Intent(context, AnalyticsEventsListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    fun searchEvents(s: String, list: List<AnalyticsEventDao>) {
        val itemList = ArrayList<AnalyticsEventDao>()
        list.let {
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
}