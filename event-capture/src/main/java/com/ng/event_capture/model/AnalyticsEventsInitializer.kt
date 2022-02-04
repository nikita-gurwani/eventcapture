package com.ng.event_capture.model

import android.content.Context
import androidx.startup.Initializer
import com.ng.event_capture.application.EventCaptureApp

internal class AnalyticsEventsInitializer : Initializer<EventCaptureApp> {

    override fun create(context: Context): EventCaptureApp {
        return EventCaptureApp(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}