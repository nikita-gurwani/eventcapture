package com.ng.event_capture

import android.content.Context
import android.content.Intent
import com.ng.event_capture.application.EventCaptureApp
import com.ng.event_capture.ui.AnalyticsEventsListActivity

object EventsCapture {

    fun start(context: Context) {
        context.startActivity(
            Intent(
                context,
                AnalyticsEventsListActivity::class.java
            ).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
    }

    fun captureEvent(name: String, properties: Map<String, String>) {
        EventCaptureApp.instance.dbManager.insertAnalyticsEvent(name, properties)
    }

}