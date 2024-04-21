package com.chamalwr.secureapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import com.chamalwr.secureapp.activities.DebugThreatAlert
import com.chamalwr.secureapp.activities.OwnerDataViewActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isDeveloperModeEnabled(this.applicationContext)) {
            val debugThreatWarning = Intent(
                this@MainActivity,
                DebugThreatAlert::class.java
            )
            startActivity(debugThreatWarning)
        } else {
            val i = Intent(
                applicationContext,
                OwnerDataViewActivity::class.java
            )
            startActivity(i)
            setContentView(R.layout.activity_owner_data_view)
        }
    }
}

fun isDeveloperModeEnabled(applicationContext: Context): Boolean {
    val isAbdEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.ADB_ENABLED, 0) != 0
    val isDebugModeEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
    return false
    return isAbdEnabled or isDebugModeEnabled
}