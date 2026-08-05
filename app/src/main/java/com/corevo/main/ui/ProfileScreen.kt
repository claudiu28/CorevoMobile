package com.corevo.main.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corevo.main.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val userDetails by viewModel.userDetails.collectAsState()
    val heatmap by viewModel.heatmap.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("My Fitness Hub", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Username: @${userDetails?.userEssentials?.username ?: "Athlete"}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Bio: ${userDetails?.bio ?: "Dedicated to daily self-improvement."}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Goal: ${userDetails?.primaryGoal?.name ?: "GENERAL FITNESS"}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${heatmap.totalWorkouts}", style = MaterialTheme.typography.titleLarge)
                    Text("Workouts", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${heatmap.totalPosts}", style = MaterialTheme.typography.titleLarge)
                    Text("Posts", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${heatmap.currentStreak}🔥", style = MaterialTheme.typography.titleLarge)
                    Text("Streak", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.logout { } },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
