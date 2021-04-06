package com.ng.analyticeventsanalyzer.model

import android.content.Context
import androidx.startup.Initializer

class AnalyticsEventsInitializer : Initializer<AnalyticsTrackingDbManager> {

    override fun create(context: Context): AnalyticsTrackingDbManager {
        //AnalyticsTrackingDbManager().init(context)
        return AnalyticsTrackingDbManager(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}