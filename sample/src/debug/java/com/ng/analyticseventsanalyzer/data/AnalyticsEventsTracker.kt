package com.ng.analyticseventsanalyzer.data

import android.content.Context
import com.ng.analyticeventsanalyzer.model.AnalyticsTrackingDbManager


//fun handleDbEvent(name: String, properties: Map<String, String>) = run {
//    val dbManager: AnalyticsTrackingDbManager = AnalyticsTrackingDbManager.instance
//    dbManager.insertAnalyticsEvent(name, properties)
//}
//
//fun startActivityAnalyticsEvent(context: Context) {
//    context.startActivity(AnalyticsTrackingDbManager.instance.getLaunchIntent(context))
//}

class AnalyticsEventsTracker {

    object eventTracker {

        fun handleDbEvent(name: String, properties: Map<String, String>) = run {
            val dbManager: AnalyticsTrackingDbManager = AnalyticsTrackingDbManager.instance
            dbManager.insertAnalyticsEvent(name, properties)
        }
    }

    object startActivityTracker {
        fun startActivityAnalyticsEvent(context: Context) {
            context.startActivity(AnalyticsTrackingDbManager.instance.getLaunchIntent(context))
        }
    }
}


