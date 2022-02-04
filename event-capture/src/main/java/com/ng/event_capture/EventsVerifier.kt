package com.ng.event_capture

import android.util.Log
import com.ng.event_capture.application.EventCaptureApp
import org.json.JSONException
import org.json.JSONObject

object EventsVerifier {

    private val repository = EventCaptureApp.instance.dbManager.repository

    fun verifyEvent(eventName: String, properties: Map<String, String>): Pair<Boolean, Int> {
        val events = repository.fetchEventsByName(eventName)
        var eventAvailable = false

        outerLoop@ for (event in events) {
            val propertiesJson = JSONObject(event.eventProperties)
            for (entry in properties) {
                try {
                    if (propertiesJson.getString(entry.key) != entry.value) {
                        Log.e("EventCapture", "\"${entry.key}\": Value mismatch")
                        continue@outerLoop
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("EventCapture", "\"${entry.key}\": Key not found")
                    continue@outerLoop
                }
            }
            eventAvailable = true
            break@outerLoop//Found an event matching all properties. No need to verify remaining events
        }
        return Pair(eventAvailable, events.size)
    }

    fun deleteAllEvents() {
        repository.deleteAllData()
    }

}