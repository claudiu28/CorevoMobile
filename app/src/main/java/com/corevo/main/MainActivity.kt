package com.corevo.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.corevo.main.ui.theme.CorevoMobileTheme

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(applicationContext)
        appContainer.realtimeService.connect()

        enableEdgeToEdge()
        setContent {
            CorevoMobileTheme {
                StartScreen(appContainer = appContainer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::appContainer.isInitialized) {
            appContainer.realtimeService.disconnect()
        }
    }
}