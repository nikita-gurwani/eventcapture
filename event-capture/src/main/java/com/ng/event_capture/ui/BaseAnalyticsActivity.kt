package com.ng.event_capture.ui

import androidx.appcompat.app.AppCompatActivity

internal abstract class BaseAnalyticsActivity : AppCompatActivity() {

    private var IN_FOREGROUND = false

    open fun isInForeground(): Boolean {
        return IN_FOREGROUND
    }

    override fun onResume() {
        super.onResume()
        IN_FOREGROUND = true
    }

    override fun onPause() {
        super.onPause()
        IN_FOREGROUND = false
    }

}