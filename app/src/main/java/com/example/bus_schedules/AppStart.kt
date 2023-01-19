package com.example.bus_schedules

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.preference.PreferenceManager

/*
This class distinguishes all type of app starts
 */
enum class AppStart {
    FIRST_TIME, FIRST_TIME_VERSION, NORMAL
}

private const val LAST_APP_VERSION = "last_app_version"

fun Context.checkAppStart(): AppStart {
    // Retrieve app version information
    val packageInfo : PackageInfo
    if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
        packageInfo = packageManager.getPackageInfo(this.packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        packageInfo = packageManager.getPackageInfo(packageName, 0)
    }
    val currentVersionCode = packageInfo.longVersionCode
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    // Retrieve the last saved version from preferences
    val lastVersionCode = sharedPreferences.getLong(LAST_APP_VERSION, -1)

    // Determine the type of app start
    val appStart = when {
        lastVersionCode == -1L -> AppStart.FIRST_TIME
        lastVersionCode < currentVersionCode -> AppStart.FIRST_TIME_VERSION
        lastVersionCode > currentVersionCode -> {
            Log.w(
                "Error", "Current version code ($currentVersionCode) is less then the one recognized on last startup ($lastVersionCode). Defensively assuming normal app start."
            )
            AppStart.NORMAL
        }
        else -> AppStart.NORMAL
    }

    // Update the saved version in preferences
    sharedPreferences.edit().putLong(LAST_APP_VERSION, currentVersionCode).apply()

    return appStart
}
