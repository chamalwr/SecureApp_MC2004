package com.chamalwr.secureapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.chamalwr.secureapp.activities.DebugThreatAlert
import com.chamalwr.secureapp.activities.OwnerDataViewActivity
import java.util.concurrent.Executor


class MainActivity :  AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isDeveloperModeEnabled(this.applicationContext)) {
            val debugThreatWarning = Intent(
                this,
                DebugThreatAlert::class.java
            )
            startActivity(debugThreatWarning)
        } else {
            executor = ContextCompat.getMainExecutor(this)
            val biometricManager = BiometricManager.from(this)
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    Log.d("AUTH_INFO", "App can authenticate using biometrics.")
                    biometricPrompt = BiometricPrompt(this, executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                val i = Intent(this@MainActivity, OwnerDataViewActivity::class.java)
                                startActivity(i)
                                setContentView(R.layout.activity_owner_data_view)
                            }

                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                Toast.makeText(this@MainActivity, "Failed to Authenticate, Cannot Proceed!", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                    promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric Authentication")
                        .setSubtitle("Login to MC2004 SecureApp")
                        .setNegativeButtonText("Cancel")
                        .build()
                    biometricPrompt.authenticate(promptInfo)
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_ERROR_NO_HARDWARE")
                    loginUsingDeviceCredentials()
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_ERROR_HW_UNAVAILABLE")
                    loginUsingDeviceCredentials()
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_ERROR_NONE_ENROLLED")
                    loginUsingDeviceCredentials()
                }
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED")
                    loginUsingDeviceCredentials()
                }

                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_ERROR_UNSUPPORTED")
                    loginUsingDeviceCredentials()
                }

                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                    Log.d("AUTH_INFO", "App cannot authenticate using biometrics due to : BIOMETRIC_STATUS_UNKNOWN")
                    loginUsingDeviceCredentials()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun loginUsingDeviceCredentials(){
        val biometricManager = BiometricManager.from(this@MainActivity)
        if (biometricManager.canAuthenticate(DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock using your device PIN, pattern, or password.")
                .setSubtitle("Login to MC2004 SecureApp")
                .setAllowedAuthenticators(DEVICE_CREDENTIAL)
                .build()

            val biometricPrompt = BiometricPrompt(this@MainActivity, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val i = Intent(this@MainActivity, OwnerDataViewActivity::class.java)
                        startActivity(i)
                        setContentView(R.layout.activity_owner_data_view)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(this@MainActivity, "Device credentials not available, Cannot Proceed!", Toast.LENGTH_LONG).show()
                    }
                })
            biometricPrompt.authenticate(promptInfo)
        } else {
            finishAffinity()
        }
    }
}

private fun isDeveloperModeEnabled(applicationContext: Context): Boolean {
    val isAbdEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.ADB_ENABLED, 0) != 0
    val isDebugModeEnabled = Settings.Secure.getInt(applicationContext.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
    return isAbdEnabled or isDebugModeEnabled
}