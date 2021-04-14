package com.ng.event_capture.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.ng.event_capture.R
import com.ng.event_capture.data.AnalyticsEventDao
import com.ng.event_capture.ui.AnalyticsEventsListActivity

class NotificationHelper(var context: Context) {

    private lateinit var notificationManager: NotificationManager
    private val ID = "eventCaptureNotification"

    fun setUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = context.getSystemService(NotificationManager::class.java)
        }
        createNotificationChannel(NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
                "eventCapture", "Event Capture notification channel.")
    }

    fun createNotificationChannel(importance: Int, showBadge: Boolean, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = ID
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun show(stickyNotification: Boolean, textToShow: MutableLiveData<List<AnalyticsEventDao>>) {
        val builder = NotificationCompat.Builder(context, ID)
                .setContentIntent(PendingIntent.getActivity(context, 0, getLaunchIntent(context), 0))
                .setLocalOnly(true)
                .setSmallIcon(R.drawable.event_black_ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.primaryColor))
                .setOngoing(stickyNotification)
                .setContentTitle("Capturing Events Logs")
        val inboxStyle = NotificationCompat.InboxStyle()
        var count = 0
        textToShow.value?.let {
            for (name: AnalyticsEventDao in it) {
                if (count == 0) {
                    builder.setContentText(name.eventName)
                }
                inboxStyle.addLine(name.eventName)
                count++
            }
        }
        builder.setAutoCancel(true)
        builder.setStyle(inboxStyle)
        notificationManager.notify(11798, builder.build())

    }

    fun getLaunchIntent(context: Context?): Intent? {
        return Intent(context, AnalyticsEventsListActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    fun dismiss() {
        notificationManager.cancel(11798)
    }
}