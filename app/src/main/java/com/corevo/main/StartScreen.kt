package com.corevo.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.corevo.main.ui.AuthScreen
import com.corevo.main.ui.MainNavigation
import com.corevo.main.viewmodel.AuthViewModel
import com.corevo.main.viewmodel.ViewModelFactory

@Composable
fun StartScreen(appContainer: AppContainer) {
    val factory = remember { ViewModelFactory(appContainer) }
    val authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
    
    val token by appContainer.sessionManager.tokenFlow.collectAsState(initial = null)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (token.isNullOrEmpty()) {
            AuthScreen(viewModel = authViewModel)
        } else {
            MainNavigation(factory = factory, appContainer = appContainer)
        }
    }
}
