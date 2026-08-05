package com.corevo.main.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corevo.main.data.model.ValidationStatus
import com.corevo.main.data.model.WorkoutPlan
import com.corevo.main.viewmodel.WorkoutsViewModel

@Composable
fun WorkoutsScreen(viewModel: WorkoutsViewModel) {
    val plans by viewModel.plans.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val availableExercises by viewModel.availableExercises.collectAsState()
    val coaches by viewModel.coaches.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    val title by viewModel.title.collectAsState()
    val desc by viewModel.description.collectAsState()
    val selectedExs by viewModel.selectedExerciseIds.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showCreateDialog = true }) {
                Text("+ Build Plan")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Text("My Workout Plans", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(plans) { plan ->
                    WorkoutPlanCard(plan = plan, onSendForReview = { coach ->
                        viewModel.sendForReview(plan.id, coach)
                    }, coaches = coaches.map { it.username })
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Build Workout Plan") },
            text = {
                Column(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { viewModel.title.value = it },
                        label = { Text("Plan Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { viewModel.description.value = it },
                        label = { Text("Description & Goals") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Select Exercises (${selectedExs.size}):", style = MaterialTheme.typography.bodyMedium)
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(availableExercises) { ex ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = selectedExs.contains(ex.id),
                                    onCheckedChange = { viewModel.toggleExerciseSelection(ex.id) }
                                )
                                Text(ex.name, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.createPlan { showCreateDialog = false } }) {
                    Text("Save Plan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun WorkoutPlanCard(plan: WorkoutPlan, onSendForReview: (String) -> Unit, coaches: List<String>) {
    var showCoachPicker by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(plan.title, style = MaterialTheme.typography.titleMedium)
                Badge(containerColor = when(plan.validationStatus) {
                    ValidationStatus.APPROVED -> MaterialTheme.colorScheme.primary
                    ValidationStatus.REJECTED -> MaterialTheme.colorScheme.error
                    ValidationStatus.PENDING -> MaterialTheme.colorScheme.tertiary
                    ValidationStatus.DRAFT -> MaterialTheme.colorScheme.secondary
                }) {
                    Text(plan.validationStatus.name, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(plan.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${plan.durationWeeks} Weeks • ${plan.difficulty} • ${plan.exercises.size} Exercises", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            
            if (plan.validationStatus == ValidationStatus.DRAFT) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { showCoachPicker = true }, modifier = Modifier.align(Alignment.End)) {
                    Text("Submit to Coach")
                }
            }
        }
    }

    if (showCoachPicker) {
        AlertDialog(
            onDismissRequest = { showCoachPicker = false },
            title = { Text("Select Coach for Review") },
            text = {
                Column {
                    coaches.forEach { coach ->
                        TextButton(onClick = {
                            onSendForReview(coach)
                            showCoachPicker = false
                        }) {
                            Text("Coach @$coach")
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showCoachPicker = false }) { Text("Cancel") } }
        )
    }
}
