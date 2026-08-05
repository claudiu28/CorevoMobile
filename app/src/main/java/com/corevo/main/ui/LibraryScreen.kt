package com.corevo.main.ui

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.corevo.main.data.model.Exercise
import com.corevo.main.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(viewModel: LibraryViewModel) {
    val exercises by viewModel.filteredExercises.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.filter(it) },
            label = { Text("Search exercises or muscle groups...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(exercises) { ex ->
                ExerciseItemCard(ex, imageLoader)
            }
        }
    }
}

@Composable
fun ExerciseItemCard(ex: Exercise, imageLoader: ImageLoader) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ex.gifUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = ex.name,
                    imageLoader = imageLoader,
                    modifier = Modifier.size(80.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = ex.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Target: ${ex.targetMuscles?.joinToString(", ") ?: "Body"}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Equipment: ${ex.equipments?.joinToString(", ") ?: "None"}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}
