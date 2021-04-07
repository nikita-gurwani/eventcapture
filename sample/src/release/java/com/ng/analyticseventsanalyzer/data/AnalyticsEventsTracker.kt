package com.ng.analyticseventsanalyzer.data

import android.content.Context


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

        }
    }

    object startActivityTracker {
        fun startActivityAnalyticsEvent(context: Context) {

        }
    }
}


