package com.ng.analyticeventsanalyzer.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ng.analyticeventsanalyzer.model.NotificationHelper;

public abstract class BaseAnalyticsActivity extends AppCompatActivity {

    private static boolean IN_FOREGROUND;

    public static boolean isInForeground() {
        return IN_FOREGROUND;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IN_FOREGROUND = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        IN_FOREGROUND = false;
    }

}
