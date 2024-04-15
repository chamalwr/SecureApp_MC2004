package com.chamalwr.secureapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chamalwr.secureapp.activities.DebugThreatAlert
import com.chamalwr.secureapp.ui.theme.SecureApp_MC2004Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isDeveloperModeEnabled(this.applicationContext)) {
            val debugThreatWarning: Intent = Intent(
                this@MainActivity,
                DebugThreatAlert::class.java
            )
            startActivity(debugThreatWarning)
        } else {
            setContent {
                SecureApp_MC2004Theme {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Greeting("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SecureApp_MC2004Theme {
        Greeting("Android")
    }
}

fun isDeveloperModeEnabled(applicationContext: Context): Boolean {
    val isAbdEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.ADB_ENABLED, 0) != 0
    val isDebugModeEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
    return isAbdEnabled or isDebugModeEnabled;
}