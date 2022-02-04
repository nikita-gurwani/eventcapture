package com.ng.event_capture.application

import android.content.Context
import com.ng.event_capture.model.AnalyticsTrackingDbManager

internal class EventCaptureApp(context: Context) {


    val dbManager: AnalyticsTrackingDbManager = AnalyticsTrackingDbManager(context)

    init {
        instance = this
    }

    companion object {
        lateinit var instance: EventCaptureApp
            private set
    }

}